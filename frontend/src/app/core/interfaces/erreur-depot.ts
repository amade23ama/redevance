import {BuilderDtoJsonAbstract} from "./BuilderDtoJsonAbstract";

export class  ErreurDepot extends BuilderDtoJsonAbstract{
    id:number;
    idDepot:number;
    message:string;
    nombreErreur:number;
}
