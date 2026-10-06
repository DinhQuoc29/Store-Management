import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PickupProductCard } from '../models/order.model';

/**
 * Service cho màn hình Pickup Checklist (gom hàng tại store).
 * Giao tiếp với /api/pickup trên Spring Boot backend.
 */
@Injectable({ providedIn: 'root' })
export class PickupService {
  private readonly http = inject(HttpClient);
  private readonly base = `${environment.apiBaseUrl}/pickup`;

  /**
   * Lấy danh sách sản phẩm cần nhặt, nhóm theo từng sản phẩm.
   *
   * Mỗi PickupProductCard chứa:
   * - Tổng số cần nhặt vs đã nhặt (hiển thị trên header card accordion)
   * - Danh sách allocations theo từng khách hàng (hiển thị khi mở rộng card)
   */
  getPickupChecklist(): Observable<PickupProductCard[]> {
    return this.http.get<PickupProductCard[]>(`${this.base}/checklist`);
  }
}
