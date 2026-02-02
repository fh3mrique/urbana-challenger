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
    private readonly userService: UserService,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    const id = Number(idParam);

    if (!idParam || Number.isNaN(id)) {
      this.snackBar.open('Id inválido na rota.', 'Fechar');
      this.router.navigate(['users']);
      return;
    }

    this.user.id = id;
    this.findById(id);
  }

  validateFields(): boolean {
    return this.nome.invalid || this.email.invalid || this.senha.invalid;
  }

  findById(id: number): void {
    this.userService.findById(id).subscribe({
      next: (response: any) => {
        // se o backend retornar nome/senha, ok. Se retornar sem senha, você decide.
        this.user.nome = response.nome ?? '';
        this.user.email = response.email ?? '';
        // senha pode não vir do backend por segurança; deixe vazia e obrigue o usuário a digitar
        this.user.senha = '';
      },
      error: (err) => {
        console.log(err);
        this.snackBar.open('Erro ao carregar usuário.', 'Fechar');
        this.router.navigate(['users']);
      }
    });
  }

  update(): void {
    // garante que o ID do body é o mesmo da URL
    const payload: IUserUpdate = {
      id: this.user.id,
      nome: this.user.nome,
      email: this.user.email,
      senha: this.user.senha
    };

    this.userService.update(this.user.id, payload).subscribe({
      next: () => {
        this.snackBar.open('Usuário atualizado com sucesso.', 'Fechar');
        this.router.navigate(['users']);
      },
      error: (err) => {
        console.log(err);
        this.snackBar.open('Erro ao atualizar usuário.', 'Fechar');
      }
    });
  }
}
