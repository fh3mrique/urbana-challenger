import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StatusLabelPipe } from './status-label.pipe';
import { BoardingPassImagePipe } from './boarding-pass-image.pipe';
import { TipoCartaoLabelPipe } from './card-type-label.pipe';

@NgModule({
  declarations: [
    StatusLabelPipe,
    BoardingPassImagePipe,
    TipoCartaoLabelPipe
  ],
  imports: [
    CommonModule
  ],
  exports: [
    StatusLabelPipe,
    BoardingPassImagePipe,
    TipoCartaoLabelPipe
  ]
})
export class PipesModule {}
