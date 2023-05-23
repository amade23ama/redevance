import {Directive, Input, TemplateRef, ViewContainerRef} from "@angular/core";
import {AuthService} from "../services/auth.service";

@Directive({
  selector:'[appAutorisation]'
})

export class AutorisationDirective {
  private droit: string[];
  constructor(private readonly authService: AuthService,
              private readonly templateRef: TemplateRef<any>, private readonly viewContainerRef: ViewContainerRef) { }

  @Input()
  set appAutorisation(value: string | string[]) {
    if (typeof value === 'string') {
      this.droit = [value];
    } else {
      this.droit = value;
    }
    this.updateView();
  }
  private updateView(): void {
    const result = this.authService.hasAnyDroits(this.droit);
    this.viewContainerRef.clear();
    if (result === true) {
      this.viewContainerRef.createEmbeddedView(this.templateRef);
    }
  }
}

