import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

type CreateUserRequest = {
  nome: string;
  email: string;
  senha: string;
};

@Component({
  selector: 'app-users-create',
  templateUrl: './users-create.component.html',
  styleUrl: './users-create.component.css'
})
export class UsersCreateComponent {

  user: CreateUserRequest = {
    nome: '',
    email: '',
    senha: '',
  };
  nome = new FormControl('', [Validators.required, Validators.minLength(3)]);
  email = new FormControl('', [Validators.required, Validators.email]);
  senha = new FormControl('', [Validators.required, Validators.minLength(3)]);

  constructor(
    private readonly _userService: UserService,
    private readonly _route: Router,
    private _snackBar: MatSnackBar
  ) {}

  validateFields(): boolean {
    return this.nome.invalid || this.email.invalid || this.senha.invalid;
  }

  create(): void {
    this._userService.create(this.user).subscribe({
      next: () => {
        this._snackBar.open('Usuário cadastrado com sucesso.', 'Fechar');
        this._route.navigate(['users']);
      },
      error: (err) => {
        console.log(err);
        this._snackBar.open('Erro ao cadastrar usuário.', 'Fechar');
      }
    });
  }
}
