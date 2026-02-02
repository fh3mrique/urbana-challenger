import { Pipe, PipeTransform } from '@angular/core';
import { TipoCartao } from '../services/card.service';

const LABELS: Record<TipoCartao, string> = {
  COMUM: 'Comum',
  ESTUDANTE: 'Estudante',
  TRABALHADOR: 'Trabalhador',
};

@Pipe({
  name: 'tipoCartaoLabel',
})
export class TipoCartaoLabelPipe implements PipeTransform {
  transform(value: TipoCartao | null | undefined): string {
    if (!value) return '';
    return LABELS[value] ?? value;
  }
}
