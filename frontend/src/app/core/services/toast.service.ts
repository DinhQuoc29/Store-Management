import { Injectable, signal } from '@angular/core';

export type ToastType = 'success' | 'error' | 'warning' | 'info';

export interface Toast {
  id: number;
  type: ToastType;
  message: string;
}

/**
 * Service quản lý toast notifications toàn ứng dụng.
 * Dùng Angular Signals để reactive state management.
 */
@Injectable({ providedIn: 'root' })
export class ToastService {
  private readonly _toasts = signal<Toast[]>([]);
  private nextId = 0;

  /** Read-only signal — component subscribe để hiển thị */
  readonly toasts = this._toasts.asReadonly();

  success(message: string, duration = 3000): void {
    this.show('success', message, duration);
  }

  error(message: string, duration = 5000): void {
    this.show('error', message, duration);
  }

  warning(message: string, duration = 4000): void {
    this.show('warning', message, duration);
  }

  info(message: string, duration = 3000): void {
    this.show('info', message, duration);
  }

  remove(id: number): void {
    this._toasts.update(toasts => toasts.filter(t => t.id !== id));
  }

  private show(type: ToastType, message: string, duration: number): void {
    const id = ++this.nextId;
    this._toasts.update(toasts => [...toasts, { id, type, message }]);
    setTimeout(() => this.remove(id), duration);
  }
}
