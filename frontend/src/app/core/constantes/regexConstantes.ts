export class RegexConstantes {

  // RegEx de validation d'une adresse mail
  static readonly REGEX_MAIL = new RegExp('[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*(\.[A-Za-z]{2,})');
  
  // RegEx de validation alphabet
  static readonly  REGEX_NOM_PRENOM = new RegExp('[A-Za-z]');
  //REGEX_NOM_PRENOM = new RegExp('(?:(?!&|\xAB|\xBB|#|{|\(|\[|\||\_|\\|\)|\]|\+|\=|\}|\*|\;|\!|\/|\$|\xA3|\:|\"|\?|\%|\.|\x47|\µ|\x40).)');

  static readonly REGEX_POSITIVE_INTEGER = '^(\d|,)*\d*$';

}