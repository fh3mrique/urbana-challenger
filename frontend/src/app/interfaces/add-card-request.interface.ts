import { TipoCartao } from "../types/card-type";

export interface AddCardRequest {
  numeroCartao: number;
  tipoCartao: TipoCartao;
}
