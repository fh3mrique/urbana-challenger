export type TipoCartao = 'COMUM' | 'ESTUDANTE' | 'TRABALHADOR';

export const TIPOS_CARTAO: { value: TipoCartao; label: string }[] = [
  { value: 'COMUM', label: 'Comum' },
  { value: 'ESTUDANTE', label: 'Estudante' },
  { value: 'TRABALHADOR', label: 'Trabalhador' },
];