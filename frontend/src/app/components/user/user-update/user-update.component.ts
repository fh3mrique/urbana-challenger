import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

export interface IUserUpdate {
  id: number;
  nome: string;
  email: string;
  senha: string;
}

@Component({
  selector: 'app-user-update',
  templateUrl: './user-update.component.html',
  styleUrl: './user-update.component.css'
})
export class UserUpdateComponent implements OnInit {

  user: IUserUpdate = {
    id: 0,
    nome: '',
    email: '',
    senha: ''
  };

  nome = new FormControl('', [Validators.required, Validators.minLength(3)]);
  email = new FormControl('', [Validators.required, Validators.email]);
  senha = new FormControl('', [Validators.required, Validators.minLength(3)]);

  constructor(
    private readonly _userService: UserService,
    private readonly _router: Router,
    private readonly _route: ActivatedRoute,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    const idParam = this._route.snapshot.paramMap.get('id');
    const id = Number(idParam);

    if (!idParam || Number.isNaN(id)) {
      this._snackBar.open('Id inválido na rota.', 'Fechar');
      this._router.navigate(['users']);
      return;
    }

    this.user.id = id;
    this.findById(id);
  }

  validateFields(): boolean {
    return this.nome.invalid || this.email.invalid || this.senha.invalid;
  }

  findById(id: number): void {
    this._userService.findById(id).subscribe({
      next: (response: any) => {
        this.user.nome = response.nome ?? '';
        this.user.email = response.email ?? '';
        this.user.senha = '';
      },
      error: (err) => {
        console.log(err);
        this._snackBar.open('Erro ao carregar usuário.', 'Fechar');
        this._router.navigate(['users']);
      }
    });
  }

  update(): void {
    const payload: IUserUpdate = {
      id: this.user.id,
      nome: this.user.nome,
      email: this.user.email,
      senha: this.user.senha
    };

    this._userService.update(this.user.id, payload).subscribe({
      next: () => {
        this._snackBar.open('Usuário atualizado com sucesso.', 'Fechar');
        this._router.navigate(['users']);
      },
      error: (err) => {
        console.log(err);
        this._snackBar.open('Erro ao atualizar usuário.', 'Fechar');
      }
    });
  }
}
