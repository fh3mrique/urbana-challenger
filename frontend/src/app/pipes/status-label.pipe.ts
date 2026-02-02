import { Pipe, PipeTransform } from '@angular/core';


@Pipe({ name: 'statusLabel' })
export class StatusLabelPipe implements PipeTransform {
  transform(value: boolean): string {
    return value ? 'Ativo' : 'Inativo';
  }
}
