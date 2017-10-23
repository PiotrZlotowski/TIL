package com.pz.til.service.email;

import com.pz.til.model.Memo;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MemoTemplateResolver extends GenericEmailTemplateResolver<Memo> {

    public MemoTemplateResolver(TemplateEngine templateEngine) {
        super(templateEngine);
    }

    @Override
    public Context setupContext(Context context, Memo base) {
        context.setVariable("message", base.getContent());
        return context;
    }

    @Override
    String getTemplateName() {
        return "html/simple-email.html";
    }
}
