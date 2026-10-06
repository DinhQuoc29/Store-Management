// ============================================================
// Product Models — mirrors Spring Boot Product DTOs
// ============================================================

/** Tương ứng ProductResponse.java */
export interface Product {
  id: number;
  productCode: string;
  productName: string;
  imageUrl: string | null;
  createdAt: string; // ISO 8601
}

/** Tương ứng ProductRequest.java — productCode do backend tự sinh */
export interface ProductRequest {
  productName: string;
  imageUrl?: string | null;
}
