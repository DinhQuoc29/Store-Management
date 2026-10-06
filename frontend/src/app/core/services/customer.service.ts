import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Customer, CustomerRequest } from '../models/customer.model';
import { PageResponse } from '../models/api.model';

/**
 * Service quản lý Khách hàng.
 * Giao tiếp với /api/customers trên Spring Boot backend.
 */
@Injectable({ providedIn: 'root' })
export class CustomerService {
  private readonly http = inject(HttpClient);
  private readonly base = `${environment.apiBaseUrl}/customers`;

  /** Tạo mới khách hàng */
  create(request: CustomerRequest): Observable<Customer> {
    return this.http.post<Customer>(this.base, request);
  }

  /** Lấy khách hàng theo ID */
  getById(id: number): Observable<Customer> {
    return this.http.get<Customer>(`${this.base}/${id}`);
  }

  /**
   * Tìm kiếm khách hàng có phân trang.
   * @param keyword Từ khóa tìm theo tên/số điện thoại/Facebook (optional)
   * @param page 0-based page index
   * @param size Số phần tử mỗi trang
   */
  search(keyword?: string, page = 0, size = 20): Observable<PageResponse<Customer>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (keyword?.trim()) {
      params = params.set('keyword', keyword.trim());
    }
    return this.http.get<PageResponse<Customer>>(this.base, { params });
  }

  /**
   * Lấy toàn bộ danh sách khách hàng (dùng cho dropdown chọn khách khi tạo đơn).
   */
  getAll(): Observable<Customer[]> {
    return this.http.get<Customer[]>(`${this.base}/all`);
  }

  /** Cập nhật toàn bộ thông tin khách hàng */
  update(id: number, request: CustomerRequest): Observable<Customer> {
    return this.http.put<Customer>(`${this.base}/${id}`, request);
  }

  /** Xóa khách hàng */
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
