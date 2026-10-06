import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CustomerService } from '../../../core/services/customer.service';
import { OrderService }    from '../../../core/services/order.service';
import { ToastService }    from '../../../core/services/toast.service';
import { Customer, CustomerRequest } from '../../../core/models/customer.model';
import { OrderSummary,
         PICKUP_STATUS_LABELS, PICKUP_STATUS_COLORS,
         PAYMENT_STATUS_LABELS, PAYMENT_STATUS_COLORS,
         SHIPPING_STATUS_LABELS, SHIPPING_STATUS_COLORS } from '../../../core/models/order.model';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [CommonModule, FormsModule, ConfirmDialogComponent],
  templateUrl: './customer-list.component.html',
  styleUrl: './customer-list.component.css'
})
export class CustomerListComponent implements OnInit {
  private readonly customerService = inject(CustomerService);
  private readonly orderService    = inject(OrderService);
  private readonly toast           = inject(ToastService);
  private readonly router          = inject(Router);

  // ── Customer list state ──────────────────────────────────────────
  customers     = signal<Customer[]>([]);
  loading       = signal(true);
  submitting    = signal(false);
  keyword       = signal('');
  currentPage   = signal(0);
  totalPages    = signal(0);
  totalElements = signal(0);
  readonly pageSize = 20;

  // ── Create / Edit modal ──────────────────────────────────────────
  showModal  = signal(false);
  editingId  = signal<number | null>(null);
  deletingId = signal<number | null>(null);

  formName     = signal('');
  formPhone    = signal('');
  formFacebook = signal('');
  formAddress  = signal('');
  formErrors   = signal<Record<string, string>>({});

  isEditing  = computed(() => this.editingId() !== null);
  modalTitle = computed(() => this.isEditing() ? 'Cập nhật khách hàng' : 'Thêm khách hàng mới');
  deletingName = computed(() =>
    this.customers().find(c => c.id === this.deletingId())?.fullName ?? ''
  );

  // ── Orders modal ─────────────────────────────────────────────────
  showOrdersModal     = signal(false);
  selectedCustomer    = signal<Customer | null>(null);
  customerOrders      = signal<OrderSummary[]>([]);
  ordersLoading       = signal(false);
  ordersTotal         = signal(0);

  // Expose label/color maps to template
  readonly pickupLabels   = PICKUP_STATUS_LABELS;
  readonly pickupColors   = PICKUP_STATUS_COLORS;
  readonly paymentLabels  = PAYMENT_STATUS_LABELS;
  readonly paymentColors  = PAYMENT_STATUS_COLORS;
  readonly shippingLabels = SHIPPING_STATUS_LABELS;
  readonly shippingColors = SHIPPING_STATUS_COLORS;

  // ── Lifecycle ────────────────────────────────────────────────────
  ngOnInit() { this.load(); }

  // ── Customer list ────────────────────────────────────────────────
  load() {
    this.loading.set(true);
    this.customerService.search(this.keyword(), this.currentPage(), this.pageSize).subscribe({
      next: p => {
        this.customers.set(p.content);
        this.totalPages.set(p.totalPages);
        this.totalElements.set(p.totalElements);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  onSearch(kw: string) {
    this.keyword.set(kw);
    this.currentPage.set(0);
    this.load();
  }

  changePage(p: number) { this.currentPage.set(p); this.load(); }

  // ── Create / Edit ────────────────────────────────────────────────
  openCreate() {
    this.editingId.set(null);
    this.clearForm();
    this.showModal.set(true);
  }

  openEdit(c: Customer) {
    this.editingId.set(c.id);
    this.formName.set(c.fullName);
    this.formPhone.set(c.phoneNumber ?? '');
    this.formFacebook.set(c.facebookUrl ?? '');
    this.formAddress.set(c.address ?? '');
    this.formErrors.set({});
    this.showModal.set(true);
  }

  closeModal() { this.showModal.set(false); }

  submit() {
    if (!this.validate()) return;
    const req: CustomerRequest = {
      fullName:    this.formName().trim(),
      phoneNumber: this.formPhone().trim()    || null,
      facebookUrl: this.formFacebook().trim() || null,
      address:     this.formAddress().trim()  || null
    };
    this.submitting.set(true);
    const obs = this.isEditing()
      ? this.customerService.update(this.editingId()!, req)
      : this.customerService.create(req);

    obs.subscribe({
      next: () => {
        this.toast.success(this.isEditing() ? 'Đã cập nhật khách hàng!' : 'Đã thêm khách hàng mới!');
        this.closeModal();
        this.load();
        this.submitting.set(false);
      },
      error: (err) => {
        const msg = err?.error?.message || 'Có lỗi xảy ra khi lưu khách hàng';
        this.toast.error(msg);
        if (msg.toLowerCase().includes('số điện thoại')) {
          this.formErrors.update(e => ({ ...e, phoneNumber: msg }));
        }
        this.submitting.set(false);
      }
    });
  }

  // ── Delete ───────────────────────────────────────────────────────
  confirmDelete(id: number) { this.deletingId.set(id); }
  cancelDelete()            { this.deletingId.set(null); }

  doDelete() {
    const id = this.deletingId();
    if (!id) return;
    this.customerService.delete(id).subscribe({
      next: () => {
        this.toast.success('Đã xóa khách hàng.');
        this.deletingId.set(null);
        this.load();
      }
    });
  }

  // ── Orders modal ─────────────────────────────────────────────────
  openOrdersModal(c: Customer) {
    this.selectedCustomer.set(c);
    this.customerOrders.set([]);
    this.ordersLoading.set(true);
    this.showOrdersModal.set(true);

    this.orderService.searchOrders({ customerId: c.id, size: 50 }).subscribe({
      next: p => {
        this.customerOrders.set(p.content);
        this.ordersTotal.set(p.totalElements);
        this.ordersLoading.set(false);
      },
      error: () => this.ordersLoading.set(false)
    });
  }

  closeOrdersModal() {
    this.showOrdersModal.set(false);
    this.selectedCustomer.set(null);
  }

  goToOrderDetail(orderId: number) {
    this.closeOrdersModal();
    this.router.navigate(['/orders', orderId]);
  }

  // ── Helpers ──────────────────────────────────────────────────────
  private validate(): boolean {
    const errs: Record<string, string> = {};
    if (!this.formName().trim()) errs['fullName'] = 'Họ tên không được để trống';
    const phone = this.formPhone().trim();
    if (phone) {
      if (!/^(\+84|0)[0-9]{8,10}$/.test(phone)) {
        errs['phoneNumber'] = 'Số điện thoại không hợp lệ (VD: 0901234567)';
      } else {
        const dup = this.customers().find(c => c.phoneNumber && c.phoneNumber.trim() === phone && c.id !== this.editingId());
        if (dup) {
          errs['phoneNumber'] = `Số điện thoại đã tồn tại (khách: ${dup.fullName})`;
        }
      }
    }
    this.formErrors.set(errs);
    return Object.keys(errs).length === 0;
  }

  private clearForm() {
    this.formName.set(''); this.formPhone.set('');
    this.formFacebook.set(''); this.formAddress.set('');
    this.formErrors.set({});
  }

  pages() { return Array.from({ length: this.totalPages() }, (_, i) => i); }

  fmt(n: number) {
    return new Intl.NumberFormat('vi-VN').format(n) + '₫';
  }

  orderRowColor(o: OrderSummary): string {
    if (o.remainingAmount <= 0) return '';
    return o.remainingAmount === o.totalAmount ? 'row--unpaid' : 'row--partial';
  }

  facebookDisplay(url: string | null): string {
    if (!url) return '';
    try {
      const u = new URL(url);
      return u.pathname.replace(/^\//, '');
    } catch {
      return url;
    }
  }
}
