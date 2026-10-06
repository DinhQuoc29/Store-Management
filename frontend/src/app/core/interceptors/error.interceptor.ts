import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { ToastService } from '../services/toast.service';
import { ApiError } from '../models/api.model';

/**
 * Functional HTTP interceptor (Angular 17+ style).
 * Xử lý lỗi HTTP tập trung — hiển thị toast thông báo khi API trả về lỗi.
 */
export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const toast = inject(ToastService);

  return next(req).pipe(
    catchError((err) => {
      const apiError = err.error as ApiError;

      if (err.status === 0) {
        // Mất kết nối hoặc CORS error
        toast.error('Không thể kết nối đến máy chủ. Vui lòng kiểm tra kết nối mạng.');
      } else if (err.status === 404) {
        toast.error(apiError?.message ?? 'Không tìm thấy dữ liệu yêu cầu.');
      } else if (err.status === 400) {
        // Validation errors có details map
        if (apiError?.details) {
          const messages = Object.values(apiError.details).join('; ');
          toast.error(`Dữ liệu không hợp lệ: ${messages}`);
        } else {
          toast.error(apiError?.message ?? 'Dữ liệu đầu vào không hợp lệ.');
        }
      } else if (err.status >= 500) {
        toast.error('Đã xảy ra lỗi máy chủ. Vui lòng thử lại sau.');
      }

      return throwError(() => err);
    })
  );
};
