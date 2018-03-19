//uikit
import uikit from 'uikit';
import 'uikit-css';

export default function(Vue, options) {
    // 向实例注入UIkit的对话框类方法
    Vue.prototype.$ui = {
        alert: uikit.modal.alert,
        confirm : function (question,options,fn,failfn) {
            Promise.resolve(uikit.modal.confirm(question,options)).then(function () {
                if(fn){
                    fn.apply(this);
                }
            }).catch(function () {
                if(failfn){
                    failfn.apply(this);
                }
            });
        },
        prompt: uikit.modal.prompt,
        block: uikit.modal.block
    }
}