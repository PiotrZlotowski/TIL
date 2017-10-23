package com.pz.til.service.email;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public abstract class GenericEmailTemplateResolver<T> {

    private TemplateEngine templateEngine;

    public GenericEmailTemplateResolver(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    abstract Context setupContext(Context context, T base);


    public String prepareEmail(T base) {
        Context context = new Context();
        Context preparedContext = setupContext(context, base);
        return templateEngine.process(getTemplateName(), preparedContext);
    }

    abstract String getTemplateName();
}
