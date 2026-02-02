import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'onlyDigits',
})
export class OnlyDigitsPipe implements PipeTransform {
  transform(value: string | null | undefined): string {
    return (value ?? '').replace(/\D+/g, '');
  }
}
