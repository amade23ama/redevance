export interface NoParamConstructor<T> {
  new(): T;
}

export abstract class BuilderDtoJsonAbstract {
  /** Constante pour le json pipe locale. */
  public static readonly JSON_DATE_PIPE_LOCALE: string = 'en-US';
  /** Constante pour le json format date. */
  public static readonly JSON_DATE_FORMAT: string = 'yyyy-MM-ddTHH:mm:ss.SSS';
  public static readonly DATE_FORMAT_BASE: string = 'dd/MM/YYYY HH:mm:ss';
  public static readonly DATE_FORMAT_SIMPLE: string = 'dd/MM/YYYY hh:mm';
  public static readonly DATE_FORMAT_SIMPLEJSON: string = 'dd-MM-YYYY hh:mm';
  public static readonly DATE_FORMAT_ANNNEJSON: string = 'YYYY';
  /**
   * convert json object to dto
   * @param json
   * @param ctor
   * @returns {T}
   */
  static fromJson<T>(json: any, ctor: NoParamConstructor<T>): T {
    // @ts-ignore
    return Object.assign(new ctor(), json);
  }

  /**
   * convert dto to json Object
   * @param {T} obj
   * @returns {any}
   */
  toJson(): any {
    return Object.assign({}, this);
  }

  /**
   *
   *
   * @template T
   * @param {NoParamConstructor<T>} ctor
   * @returns {T}
   * @memberof BuilderDtoJsonAbstract
   */
  clone<T>(ctor: NoParamConstructor<T>): T {
    // @ts-ignore
    return Object.assign(new ctor(), this.toJson());
  }

  /**
   *
   *
   * @param {BuilderDtoJsonAbstract} other
   * @returns {boolean}
   * @memberof BuilderDtoJsonAbstract
   */
  equals(other: BuilderDtoJsonAbstract): boolean {
    return JSON.stringify(this.toJson()) === JSON.stringify(other.toJson());
  }
}
