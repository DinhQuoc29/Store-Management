import {
  ApplicationConfig,
  provideBrowserGlobalErrorListeners,
  provideZoneChangeDetection
} from '@angular/core';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

import { routes } from './app.routes';
import { errorInterceptor } from './core/interceptors/error.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    // Zone.js-based change detection (zone.js có trong polyfills)
    provideZoneChangeDetection({ eventCoalescing: true }),
    // Router với component input binding (truyền route params qua @Input)
    provideRouter(routes, withComponentInputBinding()),
    // HttpClient với error interceptor
    provideHttpClient(
      withInterceptors([errorInterceptor])
    )
  ]
};
