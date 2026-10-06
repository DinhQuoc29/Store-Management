import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToastService, Toast } from '../../../core/services/toast.service';

@Component({
  selector: 'app-toast-container',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="toast-container" aria-live="polite" aria-atomic="false">
      @for (toast of toastService.toasts(); track toast.id) {
        <div class="toast toast--{{ toast.type }}" role="alert">
          <span class="toast__icon">{{ getIcon(toast.type) }}</span>
          <span class="toast__message">{{ toast.message }}</span>
          <button class="toast__close" (click)="toastService.remove(toast.id)" aria-label="Đóng">✕</button>
        </div>
      }
    </div>
  `,
  styles: [`
    .toast-container {
      position: fixed;
      top: var(--space-4);
      right: var(--space-4);
      left: var(--space-4);
      z-index: 9999;
      display: flex;
      flex-direction: column;
      gap: var(--space-2);
      pointer-events: none;
      max-width: 420px;
      margin-left: auto;
    }

    .toast {
      display: flex;
      align-items: flex-start;
      gap: var(--space-3);
      padding: var(--space-3) var(--space-4);
      border-radius: var(--radius-lg);
      backdrop-filter: blur(20px);
      border: 1px solid transparent;
      font-size: var(--font-size-sm);
      font-weight: 500;
      pointer-events: all;
      animation: fadeInUp 0.3s ease both;
      box-shadow: var(--shadow-lg);
    }

    .toast--success {
      background: rgba(52, 211, 153, 0.15);
      border-color: rgba(52, 211, 153, 0.3);
      color: var(--color-success);
    }

    .toast--error {
      background: rgba(248, 113, 113, 0.15);
      border-color: rgba(248, 113, 113, 0.3);
      color: var(--color-danger);
    }

    .toast--warning {
      background: rgba(251, 191, 36, 0.15);
      border-color: rgba(251, 191, 36, 0.3);
      color: var(--color-warning);
    }

    .toast--info {
      background: rgba(96, 165, 250, 0.15);
      border-color: rgba(96, 165, 250, 0.3);
      color: var(--color-info);
    }

    .toast__icon { font-size: 16px; flex-shrink: 0; margin-top: 1px; }

    .toast__message {
      flex: 1;
      line-height: 1.5;
      color: var(--color-text-primary);
    }

    .toast__close {
      background: none;
      border: none;
      color: var(--color-text-muted);
      cursor: pointer;
      font-size: 12px;
      padding: 2px;
      flex-shrink: 0;
      transition: color var(--transition-fast);
      margin-top: 1px;
    }

    .toast__close:hover { color: var(--color-text-primary); }
  `]
})
export class ToastContainerComponent {
  readonly toastService = inject(ToastService);

  getIcon(type: Toast['type']): string {
    const icons: Record<Toast['type'], string> = {
      success: '✅', error: '❌', warning: '⚠️', info: 'ℹ️'
    };
    return icons[type];
  }
}
