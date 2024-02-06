package cz.upol.pja.lecture07.validators;

import cz.upol.pja.tos.api.IUserManager;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;

@RequestScoped
@Named("uniqueLoginValidator")
public class UniqueLoginValidator implements Validator<String> {

    @EJB
    private IUserManager userManager;

    @Override
    public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {
        var user = userManager.findUser(value);
        if (user != null) {
            FacesMessage msg = new FacesMessage("User already exists");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
