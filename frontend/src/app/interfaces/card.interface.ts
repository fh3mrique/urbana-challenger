import { TipoCartao } from "../types/card-type";

export interface ICard {
  id: number;
  numeroCartao: number;
  tipoCartao: TipoCartao;
  status: boolean;
}