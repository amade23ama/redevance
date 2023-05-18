import { Injectable } from '@angular/core';
import { Notyf, INotyfOptions } from 'notyf';

@Injectable({
  providedIn: 'root'
})
export class NotificationService extends Notyf {
  constructor() {
    super();
    this.options.position = { x: 'center', y: 'top' };
    this.options.types = [
      {type: 'success'},
      {type: 'error',duration: 5000}
    ];

  }
}
