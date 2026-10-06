// ============================================================
// Common API Models
// ============================================================

/** Tương ứng PageResponse<T>.java */
export interface PageResponse<T> {
  content: T[];
  pageNumber: number;   // 0-based
  pageSize: number;
  totalElements: number;
  totalPages: number;
  first: boolean;
  last: boolean;
}

/** Tương ứng ErrorResponse trong GlobalExceptionHandler.java */
export interface ApiError {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  details: Record<string, string> | null; // null hoặc map field→message cho validation errors
}
