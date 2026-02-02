import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { take, finalize, catchError, EMPTY, tap } from 'rxjs';
import { UserService } from '../../../services/user.service';
import { CreateUserRequest } from '../../../interfaces/create-user-request.interface';

@Component({
  selector: 'app-users-create',
  templateUrl: './users-create.component.html',
  styleUrl: './users-create.component.css',
})
export class UsersCreateComponent {
  isSubmitting = false;

  nome = new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.minLength(3)] });
  email = new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.email] });
  senha = new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.minLength(3)] });

  constructor(
    private readonly _userService: UserService,
    private readonly _router: Router,
    private readonly _snackBar: MatSnackBar
  ) {}

  get isInvalid(): boolean {
    return this.nome.invalid || this.email.invalid || this.senha.invalid;
  }

  private markAllAsTouched(): void {
    this.nome.markAsTouched();
    this.email.markAsTouched();
    this.senha.markAsTouched();
  }

  private buildRequest(): CreateUserRequest {
    return {
      nome: this.nome.value.trim(),
      email: this.email.value.trim(),
      senha: this.senha.value,
    };
  }

  create(): void {
    this.markAllAsTouched();

    if (this.isInvalid || this.isSubmitting) return;

    this.isSubmitting = true;
    const request = this.buildRequest();

    this._userService.create(request).pipe(
      take(1),
      tap(() => {
        this._snackBar.open('Usuário cadastrado com sucesso.', 'Fechar');
        this._router.navigate(['users']);
      }),
      catchError((err) => {
        console.log(err);
        this._snackBar.open('Erro ao cadastrar usuário.', 'Fechar');
        return EMPTY;
      }),
      finalize(() => {
        this.isSubmitting = false;
      })
    ).subscribe();
  }
}
