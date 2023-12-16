import {Pipe, PipeTransform} from "@angular/core";

@Pipe({ name: 'stringifyTemplate' })
export class StringifyTemplatePipe implements PipeTransform {
  transform(template: any): string {
    if (template && template.template) {
      return '<ng-template>' + template.template.createEmbeddedView(null).rootNodes[0].outerHTML + '</ng-template>';
    } else {
      return '';
    }
  }
}

