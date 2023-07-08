import {state, style, transition, trigger} from "@angular/animations";

export const openCloseTransition = trigger('openClose', [
  state('open', style({})),
  state('closed', style({})),

  transition('* => closed', [
    // Define animation steps for transitioning from 'open' to 'closed'
  ]),
  transition('* => open', [
    // Define animation steps for transitioning from 'closed' to 'open'
  ])
]);
