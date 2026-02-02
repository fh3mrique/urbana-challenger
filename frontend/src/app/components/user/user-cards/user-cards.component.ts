import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CardService, ICard, TipoCartao } from '../../../services/card.service';

@Component({
  selector: 'app-user-cards',
  templateUrl: './user-cards.component.html',
  styleUrl: './user-cards.component.css',
})
export class UserCardsComponent implements OnInit {
  userId = 0;
  cards: ICard[] = [];

  tipos: TipoCartao[] = ['COMUM', 'ESTUDANTE', 'TRABALHADOR'];

  tipoCartao = new FormControl<TipoCartao | null>(null, {
    nonNullable: false,
    validators: [Validators.required],
  });

  numeroCartao = new FormControl<string>('', {
    nonNullable: true,
    validators: [Validators.required, Validators.minLength(6)],
  });

  displayedColumns: string[] = ['tipoCartao', 'numeroCartao', 'status', 'acoes'];

  private updatingIds = new Set<number>();

  constructor(
    private readonly _route: ActivatedRoute,
    private readonly _router: Router,
    private readonly _cardService: CardService,
    private readonly _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    const idParam = this._route.snapshot.paramMap.get('id');
    const id = Number(idParam);

    if (!idParam || Number.isNaN(id)) {
      this._snackBar.open('Id inválido na rota.', 'Fechar');
      this._router.navigate(['users']);
      return;
    }

    this.userId = id;
    this.loadCards();
  }

  loadCards(): void {
    this._cardService.findAllByUser(this.userId).subscribe({
      next: (list) => (this.cards = list),
      error: (err) => {
        console.log(err);
        this._snackBar.open('Erro ao carregar cartões.', 'Fechar');
      },
    });
  }

  addCard(): void {
    if (this.tipoCartao.invalid || this.numeroCartao.invalid) return;

    const tipo = this.tipoCartao.value;
    const numeroStr = this.numeroCartao.value;

    if (!tipo || !numeroStr) {
      this._snackBar.open('Preencha tipo e número do cartão.', 'Fechar');
      return;
    }

    this._cardService
      .addToUser(this.userId, { tipoCartao: tipo, numeroCartao: Number(numeroStr) })
      .subscribe({
        next: (created) => {
          this._snackBar.open('Cartão adicionado com sucesso.', 'Fechar');
          this.cards = [created, ...this.cards];
          this.tipoCartao.reset(null);
          this.numeroCartao.setValue('');
        },
        error: (err) => {
          console.log(err);
          this._snackBar.open('Erro ao adicionar cartão.', 'Fechar');
        },
      });
  }

  onToggleStatus(card: ICard, newStatus: boolean): void {
    if (this.isUpdating(card.id)) return;

    const previous = card.status;

    card.status = newStatus;
    this.updatingIds.add(card.id);

    this._cardService.updateStatus(this.userId, card.id, newStatus).subscribe({
      next: (updated) => {
        this.cards = this.cards.map(c => (c.id === updated.id ? updated : c));
        this._snackBar.open('Status atualizado.', 'Fechar');
        this.updatingIds.delete(card.id);
      },
      error: (err) => {
        console.log(err);
        card.status = previous;
        this.updatingIds.delete(card.id);

        const msg = err?.error?.message ?? 'Erro ao atualizar status do cartão.';
        this._snackBar.open(msg, 'Fechar');
      },
    });
  }

  isUpdating(cardId: number): boolean {
    return this.updatingIds.has(cardId);
  }

  trackByCardId(_: number, c: ICard) {
    return c.id ?? c.numeroCartao;
  }
}
