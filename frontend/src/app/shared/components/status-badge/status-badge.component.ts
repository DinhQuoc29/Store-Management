import { Component, input, computed } from '@angular/core';
import {
  PickupStatus, PaymentStatus, ShippingStatus,
  PICKUP_STATUS_LABELS, PICKUP_STATUS_COLORS,
  PAYMENT_STATUS_LABELS, PAYMENT_STATUS_COLORS,
  SHIPPING_STATUS_LABELS, SHIPPING_STATUS_COLORS
} from '../../../core/models/order.model';

type AnyStatus = PickupStatus | PaymentStatus | ShippingStatus;

@Component({
  selector: 'app-status-badge',
  standalone: true,
  template: `
    <span class="badge" [style.background]="bgColor()" [style.color]="fgColor()">
      {{ label() }}
    </span>
  `,
  styles: [`
    .badge {
      display: inline-flex; align-items: center;
      padding: 3px 10px; border-radius: 999px;
      font-size: 11px; font-weight: 700;
      letter-spacing: 0.3px; white-space: nowrap;
    }
  `]
})
export class StatusBadgeComponent {
  status = input.required<AnyStatus>();
  type   = input.required<'pickup' | 'payment' | 'shipping'>();

  label = computed(() => {
    switch (this.type()) {
      case 'pickup':   return PICKUP_STATUS_LABELS[this.status() as PickupStatus]   ?? this.status();
      case 'payment':  return PAYMENT_STATUS_LABELS[this.status() as PaymentStatus]  ?? this.status();
      case 'shipping': return SHIPPING_STATUS_LABELS[this.status() as ShippingStatus] ?? this.status();
    }
  });

  fgColor = computed(() => {
    switch (this.type()) {
      case 'pickup':   return PICKUP_STATUS_COLORS[this.status() as PickupStatus]   ?? '#fff';
      case 'payment':  return PAYMENT_STATUS_COLORS[this.status() as PaymentStatus]  ?? '#fff';
      case 'shipping': return SHIPPING_STATUS_COLORS[this.status() as ShippingStatus] ?? '#fff';
    }
  });

  bgColor = computed(() => `${this.fgColor()}22`);
}
