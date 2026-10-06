import { Component, input, output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-confirm-dialog',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="overlay" (click)="onCancel()">
      <div class="dialog" (click)="$event.stopPropagation()" role="alertdialog">
        <div class="dialog__icon">⚠️</div>
        <h3 class="dialog__title">{{ title() }}</h3>
        <p class="dialog__message">{{ message() }}</p>
        <div class="dialog__actions">
          <button class="btn btn--ghost" (click)="onCancel()">Hủy</button>
          <button class="btn btn--danger" (click)="onConfirm()">{{ confirmLabel() }}</button>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .overlay {
      position: fixed; inset: 0;
      background: rgba(0, 0, 0, 0.35);
      display: flex; align-items: center; justify-content: center;
      z-index: 1000; padding: var(--space-4);
      animation: fadeIn 0.15s ease;
    }
    .dialog {
      background: #FFFFFF;
      border: 1px solid var(--color-border);
      border-radius: var(--radius-xl);
      padding: var(--space-6);
      max-width: 360px; width: 100%;
      text-align: center;
      animation: slideDown 0.18s ease;
      box-shadow: 0 8px 24px rgba(0,0,0,0.12);
    }
    .dialog__icon { font-size: 32px; margin-bottom: var(--space-3); }
    .dialog__title { font-size: var(--font-size-md); font-weight: 700; margin-bottom: var(--space-2); color: var(--color-text-primary); }
    .dialog__message { color: var(--color-text-muted); font-size: var(--font-size-sm); margin-bottom: var(--space-5); line-height: 1.5; }
    .dialog__actions { display: flex; gap: var(--space-2); justify-content: center; }
    .btn--danger { background: #B91C1C; color: #FFFFFF; border: 1px solid #B91C1C; padding: 8px var(--space-5); border-radius: var(--radius-md); font-family: var(--font-family); font-size: var(--font-size-sm); font-weight: 600; cursor: pointer; transition: background var(--transition-fast); }
    .btn--danger:hover { background: #991B1B; border-color: #991B1B; }
  `]

})
export class ConfirmDialogComponent {
  title   = input('Xác nhận xóa');
  message = input('Bạn có chắc chắn muốn xóa? Hành động này không thể hoàn tác.');
  confirmLabel = input('Xóa');

  confirmed = output<void>();
  cancelled = output<void>();

  onConfirm() { this.confirmed.emit(); }
  onCancel()  { this.cancelled.emit(); }
}
