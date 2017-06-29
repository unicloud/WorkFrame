/*
jQWidgets v3.9.0 (2015-Oct)
Copyright (c) 2011-2015 jQWidgets.
License: http://jqwidgets.com/license/
*/

(function(a){
    a.jqx.jqxWidget("jqxFileUpload","",{});
    a.extend(a.jqx._jqxFileUpload.prototype,{
        defineInstance:function(){var b={width:null,height:"auto",uploadUrl:"",fileInputName:"",autoUpload:false,multipleFilesUpload:true,accept:null,browseTemplate:"",uploadTemplate:"",cancelTemplate:"",localization:null,renderFiles:null,disabled:false,rtl:false,events:["select","remove","uploadStart","uploadEnd"]};a.extend(true,this,b)},
        createInstance:function(){var b=this;if(b.host.jqxButton===undefined){throw new Error("jqxFileUpload: Missing reference to jqxbuttons.js")}if(a.jqx.browser.msie){if(a.jqx.browser.version<11){b._ieOldWebkit=true;if(a.jqx.browser.version<8){b._ie7=true}}}else{if(a.jqx.browser.webkit){b._ieOldWebkit=true}}b._fluidWidth=typeof b.width==="string"&&b.width.charAt(b.width.length-1)==="%";b._fluidHeight=typeof b.height==="string"&&b.height.charAt(b.height.length-1)==="%";b._render(true)},
        _render:function(b){var c=this;c._setSize();c._addClasses();if(b===true){c._appendElements()}else{c._removeHandlers()}c._addHandlers();if(c._ie7){c._borderAndPadding("width",c.host);if(c.height!=="auto"){c._borderAndPadding("height",c.host)}}a.jqx.utilities.resize(c.host,null,true);a.jqx.utilities.resize(c.host,function(){if(c._fluidWidth){if(c._ie7){c.host.css("width",c.width);c._borderAndPadding("width",c.host)}for(var f=0;f<c._fileRows.length;f++){var d=c._fileRows[f];var h=d.fileRow;if(c._ie7){h.css("width","100%");c._borderAndPadding("width",h)}if(!c.renderFiles){c._setMaxWidth(d)}}if(c.rtl&&c._ieOldWebkit){for(var e=0;e<c._forms.length;e++){var g=c._browseButton.position();c._forms[e].form.css({left:g.left,top:g.top})}}}if(c._ie7&&c._fluidHeight){c.host.css("height",c.height);c._borderAndPadding("height",c.host)}})},
        render:function(){this._render(false)},
        refresh:function(b){if(b!==true){this._render(false)}},
        destroy:function(){var b=this;b.cancelAll();b._removeHandlers(true);b.host.remove()},
        browse:function(){if(a.jqx.browser.msie&&a.jqx.browser.version<10){return}var b=this;if(b.multipleFilesUpload===true||(b.multipleFilesUpload===false&&b._fileRows.length===0)){b._forms[b._forms.length-1].fileInput.click()}},
        _uploadFile:function(b){var c=this;if(c._uploadQueue.length===0){c._uploadQueue.push(b)}if(!c.renderFiles){b.uploadFile.add(b.cancelFile).hide();b.loadingElement.show()}b.fileInput.attr("name",c.fileInputName);c._raiseEvent("2",{file:b.fileName});b.form[0].submit();c._fileObjectToRemove=b},
        uploadFile:function(b){var c=this;c._uploadFile(c._fileRows[b])},
        uploadAll:function(){var c=this;if(c._fileRows.length>0){for(var b=c._fileRows.length-1;b>=0;b--){c._uploadQueue.push(c._fileRows[b])}c._uploadFile(c._fileRows[0])}},
        cancelFile:function(b){var c=this;c._removeSingleFileRow(c._fileRows[b])},
        cancelAll:function(){var c=this;if(c._fileRows.length>0){for(var b=0;b<c._fileRows.length;b++){c._removeFileRow(c._fileRows[b])}setTimeout(function(){c._browseButton.css("margin-bottom",0)},400);c._fileRows.length=0;c._hideButtons(true)}},
        propertyChangedHandler:function(e,m,c,k){var b=e.element.id;if(m==="localization"){if(k.browseButton&&(!c||k.browseButton!==c.browseButton)){e._browseButton.text(k.browseButton);e._browseButton.jqxButton({width:"auto"})}if(k.uploadButton&&(!c||k.uploadButton!==c.uploadButton)){e._uploadButton.text(k.uploadButton);e._uploadButton.jqxButton({width:"auto"})}if(k.cancelButton&&(!c||k.cancelButton!==c.cancelButton)){e._cancelButton.text(k.cancelButton);e._cancelButton.jqxButton({width:"auto"})}if(!e.renderFiles){if(k.uploadFileTooltip&&(!c||k.uploadFileTooltip!==c.uploadFileTooltip)){a("#"+b+" .jqx-file-upload-file-upload").attr("title",k.uploadFileTooltip)}if(k.uploadFileTooltip&&(!c||k.cancelFileTooltip!==c.cancelFileTooltip)){a("#"+b+" .jqx-file-upload-file-cancel").attr("title",k.cancelFileTooltip)}}return}if(k!==c){switch(m){case"width":e.host.css("width",k);if(e._ie7){e._borderAndPadding("width",e.host);for(var d=0;d<e._fileRows.length;d++){var n=e._fileRows[d].fileRow;n.css("width","100%");e._borderAndPadding("width",n)}}e._fluidWidth=typeof k==="string"&&k.charAt(k.length-1)==="%";return;case"height":e.host.css("height",k);if(e._ie7){e._borderAndPadding("height",e.host)}e._fluidHeight=typeof k==="string"&&k.charAt(k-1)==="%";return;case"uploadUrl":for(var g=0;g<e._forms.length;g++){e._forms[g].form.attr("action",k)}return;case"accept":for(var f=0;f<e._forms.length;f++){e._forms[f].fileInput.attr("accept",k)}return;case"theme":a.jqx.utilities.setTheme(c,k,e.host);e._browseButton.jqxButton({theme:k});e._uploadButton.jqxButton({theme:k});e._cancelButton.jqxButton({theme:k});return;case"browseTemplate":e._browseButton.jqxButton({template:k});return;case"uploadTemplate":e._uploadButton.jqxButton({template:k});return;case"cancelTemplate":e._cancelButton.jqxButton({template:k});return;case"disabled":e._browseButton.jqxButton({disabled:k});e._uploadButton.jqxButton({disabled:k});e._cancelButton.jqxButton({disabled:k});if(k===true){e.host.addClass(e.toThemeProperty("jqx-fill-state-disabled"))}else{e.host.removeClass(e.toThemeProperty("jqx-fill-state-disabled"))}return;case"rtl":var h=function(l){var o=l?"addClass":"removeClass";e._browseButton[o](e.toThemeProperty("jqx-file-upload-button-browse-rtl"));e._cancelButton[o](e.toThemeProperty("jqx-file-upload-button-cancel-rtl"));e._uploadButton[o](e.toThemeProperty("jqx-file-upload-button-upload-rtl"));if(a.jqx.browser.msie&&a.jqx.browser.version>8){e._uploadButton[o](e.toThemeProperty("jqx-file-upload-button-upload-rtl-ie"))}for(var i=0;i<e._fileRows.length;i++){var j=e._fileRows[i];j.fileNameContainer[o](e.toThemeProperty("jqx-file-upload-file-name-rtl"));j.cancelFile[o](e.toThemeProperty("jqx-file-upload-file-cancel-rtl"));j.uploadFile[o](e.toThemeProperty("jqx-file-upload-file-upload-rtl"));j.loadingElement[o](e.toThemeProperty("jqx-file-upload-loading-element-rtl"))}};h(k);return}}},
        _raiseEvent:function(f,c){if(c===undefined){c={owner:null}}var d=this.events[f];c.owner=this;var e=new a.Event(d);e.owner=this;e.args=c;if(e.preventDefault){e.preventDefault()}var b=this.host.trigger(e);return b},
        _setSize:function(){var b=this;b.host.css("width",b.width);b.host.css("height",b.height)},
        _borderAndPadding:function(d,c){var b;if(d==="width"){b=parseInt(c.css("border-left-width"),10)+parseInt(c.css("border-right-width"),10)+parseInt(c.css("padding-left"),10)+parseInt(c.css("padding-right"),10)}else{b=parseInt(c.css("border-top-width"),10)+parseInt(c.css("border-bottom-width"),10)+parseInt(c.css("padding-top"),10)+parseInt(c.css("padding-bottom"),10)}c.css(d,c[d]()-b)},
        _addClasses:function(){var b=this;b.host.addClass(b.toThemeProperty("jqx-widget jqx-widget-content jqx-rc-all jqx-file-upload"));if(b.disabled===true){b.host.addClass(b.toThemeProperty("jqx-fill-state-disabled"))}},
        _appendElements:function(){var g=this,c="Browse",b=90,h="Upload All",e=90,d="Cancel All",f=90;var i=g.element.id;if(g.localization){if(g.localization.browseButton){c=g.localization.browseButton;b="auto"}if(g.localization.uploadButton){h=g.localization.uploadButton;e="auto"}if(g.localization.cancelButton){d=g.localization.cancelButton;f="auto"}}g._browseButton=a('<button id="'+i+'BrowseButton" class="'+g.toThemeProperty("jqx-file-upload-button-browse")+'">'+c+"</button>");g.host.append(g._browseButton);g._browseButton.jqxButton({theme:g.theme,width:b,template:g.browseTemplate,disabled:g.disabled});g._browseButton.after('<div style="clear: both;"></div>');g._bottomButtonsContainer=a('<div class="'+g.toThemeProperty("jqx-file-upload-buttons-container")+'"></div>');g.host.append(g._bottomButtonsContainer);g._uploadButton=a('<button id="'+i+'UploadButton" class="'+g.toThemeProperty("jqx-file-upload-button-upload")+'">'+h+"</button>");g._bottomButtonsContainer.append(g._uploadButton);g._uploadButton.jqxButton({theme:g.theme,width:e,template:g.uploadTemplate,disabled:g.disabled});g._cancelButton=a('<button id="'+i+'CancelButton" class="'+g.toThemeProperty("jqx-file-upload-button-cancel")+'">'+d+"</button>");g._bottomButtonsContainer.append(g._cancelButton);g._cancelButton.jqxButton({theme:g.theme,width:f,template:g.cancelTemplate,disabled:g.disabled});g._bottomButtonsContainer.after('<div style="clear: both;"></div>');if(g.rtl){g._browseButton.addClass(g.toThemeProperty("jqx-file-upload-button-browse-rtl"));g._cancelButton.addClass(g.toThemeProperty("jqx-file-upload-button-cancel-rtl"));g._uploadButton.addClass(g.toThemeProperty("jqx-file-upload-button-upload-rtl"));if(a.jqx.browser.msie&&a.jqx.browser.version>8){g._uploadButton.addClass(g.toThemeProperty("jqx-file-upload-button-upload-rtl-ie"))}}g._uploadIframe=a('<iframe name="'+i+'Iframe" class="'+g.toThemeProperty("jqx-file-upload-iframe")+'" src=""></iframe>');g.host.append(g._uploadIframe);g._iframeInitialized=false;g._uploadQueue=[];g._forms=[];g._addFormAndFileInput();g._fileRows=[]},
        _addFormAndFileInput:function(){
            var f=this;
            var i=f.element.id;
            var e=a('<form class="'+f.toThemeProperty("jqx-file-upload-form")+'" action="'+f.uploadUrl+'" target="'+i+'Iframe" method="post" enctype="multipart/form-data"></form>');
            f.host.append(e);
            var d=a('<input type="file" class="'+f.toThemeProperty("jqx-file-upload-file-input")+'" />');
            if(f.accept){d.attr("accept",f.accept)}
            e.append(d);
            if(f._ieOldWebkit){
                var c=f._browseButton.position();
                var g=f._browseButton.outerWidth();
                var h=f._browseButton.outerHeight();
                var b=f.rtl&&f._ie7?12:0;
                e.css({left:c.left-b,top:c.top,width:g,height:h});
                e.addClass(f.toThemeProperty("jqx-file-upload-form-ie9"));
                d.addClass(f.toThemeProperty("jqx-file-upload-file-input-ie9"));
                f.addHandler(e,"mouseenter.jqxFileUpload"+i,function(){f._browseButton.addClass(f.toThemeProperty("jqx-fill-state-hover"))});
                f.addHandler(e,"mouseleave.jqxFileUpload"+i,function(){f._browseButton.removeClass(f.toThemeProperty("jqx-fill-state-hover"))});
                f.addHandler(e,"mousedown.jqxFileUpload"+i,function(){f._browseButton.addClass(f.toThemeProperty("jqx-fill-state-pressed"))});
                f.addHandler(a(document),"mouseup.jqxFileUpload"+i,function(){if(f._browseButton.hasClass("jqx-fill-state-pressed")){f._browseButton.removeClass(f.toThemeProperty("jqx-fill-state-pressed"))}})
            }
            f.addHandler(d,"change.jqxFileUpload"+i,function(){
                var l=this.value,j;
                if(!a.jqx.browser.mozilla){if(l.indexOf("fakepath")!==-1){l=l.slice(12)}else{l=l.slice(l.lastIndexOf("\\")+1)}}
                if(a.jqx.browser.msie&&a.jqx.browser.version<10){j="IE9 and earlier do not support getting the file size."}else{j=this.files[0].size}
                var k=f._addFileRow(l,e,d,j);
                if(f._fileRows.length===1){f._browseButton.css("margin-bottom","10px");f._hideButtons(false)}
                if(f._ieOldWebkit){f.removeHandler(e,"mouseenter.jqxFileUpload"+i);f.removeHandler(e,"mouseleave.jqxFileUpload"+i);f.removeHandler(e,"mousedown.jqxFileUpload"+i)}
                f._addFormAndFileInput();
                f.removeHandler(d,"change.jqxFileUpload"+i);
                if(f.autoUpload===true){f._uploadFile(k)};
            });
            if(f._ieOldWebkit===true){
                f.addHandler(d,"click.jqxFileUpload"+i,function(j){if(f.multipleFilesUpload===false&&f._fileRows.length>0){j.preventDefault()}})
            }
            f._forms.push({form:e,fileInput:d})
        },
        _addFileRow:function(f,b,e,d){
            var h=this,l,g,m,j,n,i="Cancel",k="Upload File";
            l=a('<div class="'+h.toThemeProperty("jqx-widget-content jqx-rc-all jqx-file-upload-file-row")+'"></div>');
            if(h._fileRows.length===0){h._browseButton.after(l)}else{h._fileRows[h._fileRows.length-1].fileRow.after(l)}
            if(!h.renderFiles){
                g=a('<div class="'+h.toThemeProperty("jqx-widget-header jqx-rc-all jqx-file-upload-file-name")+'">'+f+"</div>");
                l.append(g);
                if(h.localization){
                    if(h.localization.cancelFileTooltip){i=h.localization.cancelFileTooltip}
                    if(h.localization.uploadFileTooltip){k=h.localization.uploadFileTooltip}
                }
                j=a('<div class="'+h.toThemeProperty("jqx-widget-header jqx-rc-all jqx-file-upload-file-cancel")+'" title="'+i+'"><div class="'+h.toThemeProperty("jqx-icon-close jqx-file-upload-icon")+'"></div></div>');
                l.append(j);
                n=a('<div class="'+h.toThemeProperty("jqx-widget-header jqx-rc-all jqx-file-upload-file-upload")+'" title="'+k+'"><div class="'+h.toThemeProperty("jqx-icon-arrow-up jqx-file-upload-icon jqx-file-upload-icon-upload")+'"></div></div>');
                l.append(n);
                m=a('<div class="'+h.toThemeProperty("jqx-file-upload-loading-element")+'"></div>');
                l.append(m);
                if(h.rtl){
                    g.addClass(h.toThemeProperty("jqx-file-upload-file-name-rtl"));
                    j.addClass(h.toThemeProperty("jqx-file-upload-file-cancel-rtl"));
                    n.addClass(h.toThemeProperty("jqx-file-upload-file-upload-rtl"));
                    m.addClass(h.toThemeProperty("jqx-file-upload-loading-element-rtl"))
                }
                h._setMaxWidth({fileNameContainer:g,uploadFile:n,cancelFile:j})
            }else{
                l.html(h.renderFiles(f))
            }
            if(h._ie7){
                h._borderAndPadding("width",l);
                h._borderAndPadding("height",l);
                if(!h.renderFiles){
                    h._borderAndPadding("height",g);
                    h._borderAndPadding("height",n);
                    h._borderAndPadding("height",j)
                }
            }
            var c={fileRow:l,fileNameContainer:g,fileName:f,uploadFile:n,cancelFile:j,loadingElement:m,form:b,fileInput:e,index:h._fileRows.length};
            h._addFileHandlers(c);
            h._fileRows.push(c);
            h._raiseEvent("0",{file:f,size:d});
            return c
        },
        _setMaxWidth:function(c){
            var e=this;
            var f=c.cancelFile.outerWidth(true)+c.uploadFile.outerWidth(true);
            var b=e._ie7?6:0;
            var d=e.host.width()-parseInt(e.host.css("padding-left"),10)-parseInt(e.host.css("padding-right"),10)-f-b-7;
            c.fileNameContainer.css("max-width",d)
        },
        _addFileHandlers:function(b){
            var c=this;
            if(!c.renderFiles){
                var d=c.element.id;
                c.addHandler(b.uploadFile,"mouseenter.jqxFileUpload"+d,function(){
                    if(c.disabled===false){b.uploadFile.addClass(c.toThemeProperty("jqx-fill-state-hover"))}
                });
                c.addHandler(b.uploadFile,"mouseleave.jqxFileUpload"+d,function(){
                    if(c.disabled===false){b.uploadFile.removeClass(c.toThemeProperty("jqx-fill-state-hover"))}
                });
                c.addHandler(b.uploadFile,"click.jqxFileUpload"+d,function(){
                    if(c.disabled===false){c._uploadFile(b)}
                });
                c.addHandler(b.cancelFile,"mouseenter.jqxFileUpload"+d,function(){
                    if(c.disabled===false){b.cancelFile.addClass(c.toThemeProperty("jqx-fill-state-hover"))}
                });
                c.addHandler(b.cancelFile,"mouseleave.jqxFileUpload"+d,function(){
                    if(c.disabled===false){b.cancelFile.removeClass(c.toThemeProperty("jqx-fill-state-hover"))}
                });
                c.addHandler(b.cancelFile,"click.jqxFileUpload"+d,function(){
                    if(c.disabled===false){c._removeSingleFileRow(b)}
                })
            }
        },
        _removeSingleFileRow:function(c){
            var d=this;
            d._removeFileRow(c);
            d._fileRows.splice(c.index,1);
            if(d._fileRows.length===0){
                setTimeout(function(){d._browseButton.css("margin-bottom",0)},400);d._hideButtons(true)
            }else{
                for(var b=0;b<d._fileRows.length;b++){d._fileRows[b].index=b}
            }
        },
        _removeFileRow:function(b){
            var c=this;
            var d=c.element.id;
            if(!c.renderFiles){
                c.removeHandler(b.uploadFile,"mouseenter.jqxFileUpload"+d);
                c.removeHandler(b.uploadFile,"mouseleave.jqxFileUpload"+d);
                c.removeHandler(b.uploadFile,"click.jqxFileUpload"+d);
                c.removeHandler(b.cancelFile,"mouseenter.jqxFileUpload"+d);
                c.removeHandler(b.cancelFile,"mouseleave.jqxFileUpload"+d);
                c.removeHandler(b.cancelFile,"click.jqxFileUpload"+d)
            }
            b.fileRow.fadeOut(function(){b.fileRow.remove();b.form.remove()});
            c._raiseEvent("1",{file:b.fileName})
        },
        _hideButtons:function(b){
            var c=this;
            if(b===true){c._bottomButtonsContainer.fadeOut()}else{c._bottomButtonsContainer.fadeIn()}
        },
        _addHandlers:function(){
            var b=this;
            var c=b.element.id;
            if(!b._ieOldWebkit){
                b.addHandler(b._browseButton,"click.jqxFileUpload"+c,function(){b.browse()})
            }
            b.addHandler(b._uploadButton,"click.jqxFileUpload"+c,function(){b.uploadAll()});
            b.addHandler(b._cancelButton,"click.jqxFileUpload"+c,function(){b.cancelAll()});
            b.addHandler(b._uploadIframe,"load.jqxFileUpload"+c,function(){
                if(a.jqx.browser.chrome||a.jqx.browser.webkit){b._iframeInitialized=true}
                if(b._iframeInitialized===false){
                    b._iframeInitialized=true
                }else{
                    var d=b._uploadIframe.contents().find("body").html();
                    b._raiseEvent("3",{file:b._uploadQueue[b._uploadQueue.length-1].fileName,response:d});
                    if(b._fileObjectToRemove){b._removeSingleFileRow(b._fileObjectToRemove);b._fileObjectToRemove=null}
                    b._uploadQueue.pop();
                    if(b._uploadQueue.length>0){b._uploadFile(b._uploadQueue[b._uploadQueue.length-1])}
                }
            })
        },
        _removeHandlers:function(b){
            var d=this;var e=d.element.id;
            d.removeHandler(d._browseButton,"click.jqxFileUpload"+e);
            d.removeHandler(d._uploadButton,"click.jqxFileUpload"+e);
            d.removeHandler(d._cancelButton,"click.jqxFileUpload"+e);
            d.removeHandler(d._uploadIframe,"load.jqxFileUpload"+e);
            if(b===true){
                var c=d._forms[d._forms.length-1];
                d.removeHandler(c.fileInput,"change.jqxFileUpload"+e);
                if(d._ieOldWebkit){
                    d.removeHandler(c.form,"mouseenter.jqxFileUpload"+e);
                    d.removeHandler(c.form,"mouseleave.jqxFileUpload"+e);
                    d.removeHandler(c.form,"mousedown.jqxFileUpload"+e);
                    d.removeHandler(a("body"),"mouseup.jqxFileUpload"+e)
                }
            }
        }
    })
})(jqxBaseFramework);