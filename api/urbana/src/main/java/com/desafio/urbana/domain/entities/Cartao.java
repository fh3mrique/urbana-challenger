package com.desafio.urbana.domain.entities;

import com.desafio.urbana.domain.enums.TipoCartao;
import jakarta.persistence.*;

@Entity
@Table(
        name = "tb_cartoes",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_cartao_numero", columnNames = "numero_cartao")
        }
)
public class Cartao {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(name = "numero_cartao", nullable = false)
 private Long numeroCartao;


 @Column(nullable = false)
 private Boolean status;

 @Enumerated(EnumType.STRING)
 @Column(name = "tipo_cartao", nullable = false, length = 20)
 private TipoCartao tipoCartao;

 @ManyToOne(optional = false, fetch = FetchType.LAZY)
 @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cartao_usuario"))
 private Usuario usuario;

 public Cartao() {}

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public Long getNumeroCartao() {
  return numeroCartao;
 }

 public void setNumeroCartao(Long numeroCartao) {
  this.numeroCartao = numeroCartao;
 }

 public Boolean getStatus() {
  return status;
 }

 public void setStatus(Boolean status) {
  this.status = status;
 }

 public TipoCartao getTipoCartao() {
  return tipoCartao;
 }

 public void setTipoCartao(TipoCartao tipoCartao) {
  this.tipoCartao = tipoCartao;
 }

 public Usuario getUsuario() {
  return usuario;
 }

 public void setUsuario(Usuario usuario) {
  this.usuario = usuario;
 }
}
