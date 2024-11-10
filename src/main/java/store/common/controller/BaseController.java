package store.common.controller;

import store.common.view.InputView;
import store.common.view.OutputView;

public abstract class BaseController {
    protected final InputView inputView;
    protected final OutputView outputView;

    protected BaseController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }
}
