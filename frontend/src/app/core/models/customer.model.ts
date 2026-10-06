// ============================================================
// Customer Models — mirrors Spring Boot Customer DTOs
// ============================================================

/** Tương ứng CustomerResponse.java */
export interface Customer {
  id: number;
  fullName: string;
  facebookUrl: string | null;
  phoneNumber: string | null;
  address: string | null;
  createdAt: string; // ISO 8601
}

/** Tương ứng CustomerRequest.java */
export interface CustomerRequest {
  fullName: string;
  facebookUrl?: string | null;
  phoneNumber?: string | null;
  address?: string | null;
}
