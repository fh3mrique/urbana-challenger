import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'boardingPassImage' })
export class BoardingPassImagePipe implements PipeTransform {
  transform(type: string): string {
    switch (type) {
      case 'TRABALHADOR':
        return 'https://www.urbana-pe.com.br/wp-content/uploads/2019/09/cartao-vem-trabalhador.png';
      case 'COMUM':
        return 'https://www.urbana-pe.com.br/wp-content/uploads/2019/09/cartao-vem-comum.png';
      case 'ESTUDANTE':
        return 'https://www.urbana-pe.com.br/wp-content/uploads/2019/09/cartao-vem-estudante.png';
      default:
        return 'path/to/default-image.png';
    }
  }
}
