import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CardService, ICard, TipoCartao } from '../../../services/card.service';

@Component({
  selector: 'app-user-cards',
  templateUrl: './user-cards.component.html',
  styleUrl: './user-cards.component.css'
})
export class UserCardsComponent implements OnInit {

  userId = 0;
  cards: ICard[] = [];

  tipos: { value: TipoCartao; label: string }[] = [
    { value: 'COMUM', label: 'Comum' },
    { value: 'ESTUDANTE', label: 'Estudante' },
    { value: 'TRABALHADOR', label: 'Trabalhador' },
  ];

  tipoCartao = new FormControl<TipoCartao | null>(null, [Validators.required]);
  numeroCartao = new FormControl<string>('', [Validators.required, Validators.minLength(6)]);

  displayedColumns: string[] = ['tipoCartao', 'numeroCartao', 'status'];

  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly cardService: CardService,
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

    this.userId = id;
    this.loadCards();
  }

  loadCards(): void {
    this.cardService.findAllByUser(this.userId).subscribe({
      next: (list) => this.cards = list,
      error: (err) => {
        console.log(err);
        this.snackBar.open('Erro ao carregar cartões.', 'Fechar');
      }
    });
  }

  addCard(): void {
    const tipo = this.tipoCartao.value;
    const numero = Number(this.numeroCartao.value);

    if (!tipo) {
      this.snackBar.open('Tipo do cartão é obrigatório.', 'Fechar');
      return;
    }
    if (Number.isNaN(numero)) {
      this.snackBar.open('Número do cartão inválido.', 'Fechar');
      return;
    }

    this.cardService.addToUser(this.userId, { tipoCartao: tipo, numeroCartao: numero }).subscribe({
      next: (created) => {
        this.snackBar.open('Cartão adicionado com sucesso.', 'Fechar');
        this.cards = [created, ...this.cards];
        this.tipoCartao.reset(null);
        this.numeroCartao.reset('');
      },
      error: (err) => {
        console.log(err);
        this.snackBar.open('Erro ao adicionar cartão.', 'Fechar');
      }
    });
  }
}
