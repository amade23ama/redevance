import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AppConfigService {
  private config: any = null;

  private label: any = null;

  static configLogger: any = null;
  constructor(private readonly http: HttpClient) {
  }

  /**
   * Recuperation du label par sa cle
   * @param key
   * @param args
   */
  public getLabel(key: any, ...args: any) {
    if (this.label != null) {
      const val = this.label['properties'][key];
      if (val === undefined) return key;
      return this.formatString(val, args);
    }
  }

  /**
   * Recuperation du config par sa cle
   * @param {*} key
   * @returns
   * @memberof AppConfigService
   */
  public getConfig(key: any) {
    if (this.config != null) {
      return this.config[key];
    }
  }

  /**
   * Chargement des labels
   *
   * @returns
   * @memberof AppConfigService
   */
  /*
  async loadLabel() {
    return this.http.get(SERVER_API_URL + '/prop/label').pipe(
      tap((label) => this.label = label)
    ).toPromise();
  }
  */

  /**
   * Chargement des configs
   *
   * @returns
   * @memberof AppConfigService
   */
  /*async loadConfig() {
    return this.http.get(SERVER_API_URL + '/prop/conf').pipe(
      tap((conf) => this.config = conf)
    ).toPromise();
  }
  */


  /**
   * @private
   * @param {string} str
   * @param {...string[]} val
   * @returns
   * @memberof AppConfigService
   */
  private formatString(str: string, ...val: string[]) {
    for (let index = 0; index < val.length; index++) {
      str = str.replace(`{${index}}`, val[index]);
    }
    return str;
  }

  /**
   * Chargement du label en local
   *
   * @returns
   * @memberof AppConfigService
   */
  loadLocalLabel() {
    return this.http.get('/assets/label/label.json').pipe(
      tap((label) => {
        this.label = label;
      })
    ).toPromise();
  }

  /**
   * Recuperation la configuration des loggers.
   *
   * @returns
   * @memberof AppConfigService
   */

  /*async loadLoggerConfig() {
    return this.http.get(SERVER_API_URL + '/prop/loggers').pipe(
      map((res) => AppConfigService.configLogger = res)
    ).toPromise();
  }
  */
}
