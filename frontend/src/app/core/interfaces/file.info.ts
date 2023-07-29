import {BuilderDtoJsonAbstract} from "./BuilderDtoJsonAbstract";

export class FileInfo  extends  BuilderDtoJsonAbstract{
  enteteFile:string[];
  colonneTable: string[];
  headerFileToDatabase: Map<string, any>

  toJson(): any {
    return {
      ...super.toJson(),
      headerFileToDatabase :this.headerFileToDatabase? JSON.stringify(Object.fromEntries(this.headerFileToDatabase)):null,
      colonneTable :this.colonneTable?JSON.stringify(this.colonneTable):null,
      enteteFile :this.enteteFile?JSON.stringify(this.enteteFile):null
    };
  }
}
