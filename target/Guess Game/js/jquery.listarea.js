/*!
 * jQuery listarea plugin
 * Original author: Omar Wheatley
 * Copyright 2014 Omar Wheatley
 * Licensed under the MIT license
 * 1.0.0
 */;
(function($, window, undefined) {
    $.fn.listarea = function(params) {
        params = $.extend( {delimiter: ',', effect: null, placeholder: "输入"}, params);
        var textareaInput = $(this).hide().after('<div class="addListItem"><input class="input1" type="text" placeholder="'+params.placeholder+'卡牌名称"/><input class="input2" type="text" placeholder="'+params.placeholder+'卡牌成色"/><input class="input3" type="text" placeholder="'+params.placeholder+'卡牌价值"/><div class="addButton"></div></div>');
        var listareaWrap = $('div.addListItem');
        $('div.addListItem > .addButton').on('click', function(){
            lbAddListItem(textareaInput, listareaWrap, params);
        });
        $('div.addListItem').on('click', 'div.listItemWrap .deleteButton', function(){
            lbDeleteListItem(textareaInput, listareaWrap, $(this), params);
        });
        $('div.addListItem > input').on('enterKey', function(){
            alert('in');
            return false;
        });
        $('div.addListItem > input').keyup(function(e){
            if(e.keyCode == 13)
            {
                alert('in');
                $(this).trigger("enterKey");
            }
            return false;
        });
        return this;
    };
})( jQuery, window );


/* 添加列*/
function lbAddListItem(textareaInput, listareaWrap, params){
    //var itemValue = $('div.addListItem > input').val();
    var itemValue = $('div.addListItem > .input1').val();
    var itemValue1 = $('div.addListItem > .input2').val();
    var itemValue2 = $('div.addListItem > .input3').val();

    if(itemValue != ''&&itemValue1!='' && itemValue2!=''){
        textareaInput.next('div.addListItem').find('input').val('');
        $('<div class="listItemWrap"><div class="listItem">' + itemValue + '</div><div class="listItem" style="margin-left:20px">' + itemValue1 + '</div><div class="listItem" style="margin-left:20px">' + itemValue2 + '</div><div class="deleteButton"></div></div>').hide().prependTo(listareaWrap).show(params.effect);
        lbListItemToTextArea(textareaInput, listareaWrap, params);
    }
}

function lbDeleteListItem(textareaInput, listareaWrap, item, params){
    item.parent().remove();
    lbListItemToTextArea(textareaInput, listareaWrap, params);
}

function lbListItemToTextArea(textareaInput, listareaWrap, params){
    var items = [];
    listareaWrap.find('div.listItem').each(function(){
        items.push( $(this).text() );
    });
    textareaInput.val(items.join(params.delimiter));
}
