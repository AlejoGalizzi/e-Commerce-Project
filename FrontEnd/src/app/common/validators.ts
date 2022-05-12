import { FormGroup, ValidationErrors } from "@angular/forms";

export class Validators {
  static notOnlyWhiteSpace(control: FormGroup): ValidationErrors {
    if((control.value != null) && (control.value.trim().length === 0)) {
      return {'notOnlyWhiteSpace': true};
    }else{
      return null;
    }
  }
}
