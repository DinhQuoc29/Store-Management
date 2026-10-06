import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Product, ProductRequest } from '../models/product.model';
import { PageResponse } from '../models/api.model';

/**
 * Service quản lý Sản phẩm.
 */
@Injectable({ providedIn: 'root' })
export class ProductService {
  private readonly http = inject(HttpClient);
  private readonly base = `${environment.apiBaseUrl}/products`;

  /** Tạo mới sản phẩm */
  create(request: ProductRequest): Observable<Product> {
    return this.http.post<Product>(this.base, request);
  }

  /** Lấy sản phẩm theo ID */
  getById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.base}/${id}`);
  }

  /**
   * Tìm kiếm sản phẩm có phân trang.
   * @param keyword Từ khóa tìm theo tên/mã sản phẩm
   * @param page 0-based page index
   * @param size Số phần tử mỗi trang
   */
  search(keyword?: string, page = 0, size = 20): Observable<PageResponse<Product>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (keyword?.trim()) {
      params = params.set('keyword', keyword.trim());
    }
    return this.http.get<PageResponse<Product>>(this.base, { params });
  }

  /** Lấy toàn bộ danh sách sản phẩm (dùng cho dropdown). */
  getAll(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.base}/all`);
  }

  /** Cập nhật sản phẩm */
  update(id: number, request: ProductRequest): Observable<Product> {
    return this.http.put<Product>(`${this.base}/${id}`, request);
  }

  /** Xóa sản phẩm */
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
