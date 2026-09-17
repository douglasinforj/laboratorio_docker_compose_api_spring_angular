import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from './usuario.service';
import { Usuario } from './usuario.model';

@Component({
  selector: 'app-usuarios',
  imports: [CommonModule, FormsModule],
  templateUrl: './usuarios.html',
  styleUrl: './usuarios.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class Usuarios implements OnInit {

  private service = inject(UsuarioService);

  usuarios  = signal<Usuario[]>([]);
  mensagem  = signal('');
  editando  = signal(false);

  form: Usuario = { nome: '', email: '', telefone: '' };
  private idEditando?: number;

  ngOnInit() { this.carregar(); }

  carregar() {
    this.service.listar().subscribe(data => this.usuarios.set(data));
  }

  salvar() {
    if (!this.form.nome || !this.form.email) {
      this.mensagem.set('⚠️ Nome e e-mail são obrigatórios.');
      return;
    }
    const op = this.editando()
      ? this.service.atualizar(this.idEditando!, this.form)
      : this.service.criar(this.form);

    op.subscribe({
      next: () => {
        this.mensagem.set(this.editando() ? '✅ Atualizado!' : '✅ Cadastrado!');
        this.cancelar();
        this.carregar();
        setTimeout(() => this.mensagem.set(''), 3000);
      },
      error: err => this.mensagem.set(`❌ Erro: ${err.error?.erro ?? 'Tente novamente'}`)
    });
  }

  editar(u: Usuario) {
    this.form       = { nome: u.nome, email: u.email, telefone: u.telefone };
    this.idEditando = u.id;
    this.editando.set(true);
  }

  cancelar() {
    this.form       = { nome: '', email: '', telefone: '' };
    this.idEditando = undefined;
    this.editando.set(false);
  }

  deletar(id: number) {
    if (!confirm('Confirma exclusão?')) return;
    this.service.deletar(id).subscribe(() => {
      this.mensagem.set('🗑️ Removido!');
      this.carregar();
      setTimeout(() => this.mensagem.set(''), 3000);
    });
  }
}