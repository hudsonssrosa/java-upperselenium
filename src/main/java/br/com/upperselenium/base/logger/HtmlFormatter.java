package br.com.upperselenium.base.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import br.com.upperselenium.base.exception.RunningException;
import br.com.upperselenium.base.constant.FileDirPRM;
import br.com.upperselenium.base.constant.MessagePRM;

/**
 * Classe para formatação das entradas de Log para um padrão HTML.
 */
public class HtmlFormatter extends BaseLogger {
	
	/**
	 * Método que retorna a estrutura inicial de um documento HTML.<br>
	 * Deve-se passar uma String para a tag <b>&lt;title&gt;</b><br><br>
	 * 
	 * <p style="color:red">Ordem de Parâmetros: Title</p><br><br>
	 * 
	 * Como retorno, tem-se as tags: <b>&lt;DOCTYPE html&gt; &lt;html&gt; &lt;title&gt;</b>.
	 * 
	 * @param title
	 * @return
	 */
	public static String getInitialHtmlTags(String title) {
		if (title != null){
			return "<!DOCTYPE html>\n<html>\n" + "<title>" + title + "</title>\n";
		}
		return "<!DOCTYPE html>\n<html>\n";
	}
	
	/**
	 * Método que retorna uma String com o conteúdo <b>&lt;body&gt;</b>.<br>
	 * Caso necessário, pode-se fornecer um parâmetro para o estilo CSS desta tag.<br>
	 * 
	 * <p style="color:red"><p style="color:red">Ordem de Parâmetros: Style</p><br><br>
	 * 
	 * Como resultado, obtem-se a tag <b>&lt;body&gt;</b> simples ou <b>&lt;body&gt; com o parâmetro <i>style</i></b> definido.<br><br>
	 * <i>Exemplos:<br>
	 * 			&lt;body&gt;<br>
	 *   		&lt;body class="estilo"&gt;<br>
	 *   		&lt;body style="color:red;margin-left:13px"&gt;</i>
	 *		
	 * @param style
	 * @return
	 */
	public static String writeBodyOpened(String style) {
		if (style != null){
			return "<body " + style + ">";
		}
		return "<body>\n";
	}

	/**
	 * Método que retorna uma String com o conteúdo <b>&lt;head&gt;</b>.
	 *		
	 * @return
	 */
	public static String writeHeadOpened() {
		return "<head>\n";
	}

	/**
	 * Método que retorna uma String com o import do CSS no HTML:<br>
	 * <b>&lt;linkRel="[parâmetro pathNamelinkRel]" href="[parâmetro hRef]"&gt;</b>.<br>
	 * Deve ser utilizado após a abertura da tag <b>&lt;head&gt;</b>.<br>
	 * Se os parâmetros não forem passados ao método, o retorno será <b>vazio</b>.
	 *
	 * <p style="color:red">Ordem de Parâmetros: href-CSS-File</p><br><br>
	 *		
	 * @param hRefCSS
	 * @return
	 */
	public static String writeCssImportOnHead(String hRefCSS) {
		if (hRefCSS != null){
			String linkHead = "<link rel=\"stylesheet\" type=\"text/css\" " + "href=" + "\"" + hRefCSS + "\">\n";
			return linkHead;
		}
		return "";
	}
	
	/**
	 * Método que retorna uma String com import de arquivo JScript no HTML:<br>
	 * <b>&lt;script src="[parâmetro src]"&gt;&lt;/script&gt;</b>.<br>
	 * Deve ser utilizado após a abertura da tag <b>&lt;head&gt;</b>.<br>
	 * Se os parâmetros não forem passados ao método, o retorno será <b>vazio</b>.
	 *
	 * <p style="color:red">Ordem de Parâmetros: Src-JScript-File</p><br><br>
	 *		
	 * @param srcJScript
	 * @return
	 */
	public static String writeJScriptImportOnHead(String srcJScript) {
		if (srcJScript != null){
			String linkHead = "<script type=\"text/javascript\" src=\"" + srcJScript + "\"></script>";
			return linkHead;
		}
		return "";
	}

	/**
	 * Método que retorna uma String com o conteúdo de import no HTML:<br>
	 * <b>&lt;script src="[parâmetro pathNameScriptHead]"&gt; &lt;/script&gt;.</b>.<br>
	 * Deve ser utilizado após a abertura da tag <b>&lt;head&gt;</b>.<br>
	 * Se os parâmetros não forem passados ao método, o retorno será <b>vazio</b>.
	 *
	 * <p style="color:red">Ordem de Parâmetros: Src-Path-JScript</p><br><br>
	 *		
	 * @param pathNameScriptHead
	 * @return
	 */
	public static String writeScriptImportsOnHead(String pathNameScriptHead) {
		if (pathNameScriptHead != null){
			String scriptLinkHead = "<script src=" + "\"" + pathNameScriptHead + "\">" + "</script>\n";
			return scriptLinkHead;
		}
		return "";
	}

	/**
	 * Método que retorna uma String com o conteúdo de import no HTML:<br>
	 * <b>&lt;script type="[parâmetro pathNameScriptHead]"&gt; &lt;/script&gt;.</b>.<br>
	 * Deve ser utilizado após a abertura da tag <b>&lt;head&gt;</b>.<br>
	 * Se os parâmetros não forem passados ao método, o retorno será <b>vazio</b>.
	 *
	 * <p style="color:red">Ordem de Parâmetros: Type-Text-JScript</p><br><br>
	 *		
	 * @param scriptHead
	 * @return
	 */
	public static String writeScriptTypeJSImportsOnHead(String scriptHead) {
		if (scriptHead != null){
			String scriptOnHead = "<script type=\"text/javascript\" >" + scriptHead + "</script>\n";
			return scriptOnHead;
		}
		return "";
	}
	
	/**
	 * Método que retorna uma String com o conteúdo <b>&lt;style&gt;[parâmetro com conteúdo do CSS]&lt;/style&gt;</b>.<br>
	 * Deve ser utilizado após a abertura da tag <b>&lt;head&gt;</b>.<br>
	 * Se os parâmetros não forem passados ao método, o retorno será <b>vazio</b>.
	 * 
	 * <p style="color:red">Ordem de Parâmetros: Css-Head</p><br><br>
	 * 
	 * @param cssStyleOnHead
	 * @return
	 */
	public static String writeStyleOnHead(String cssStyleOnHead) {
		if (cssStyleOnHead != null){
			String styleWrite = "<style>" + cssStyleOnHead + "</style>\n";
			return styleWrite;
		}
		return "";
	}
	
	/**
	 * Método que retorna uma String com o conteúdo <b>&lt;script&gt;[parâmetro com conteúdo do script (javascript, por exemplo)]&lt;/script&gt;</b>.<br>
	 * Deve ser utilizado após a abertura da tag <b>&lt;head&gt;</b>.<br>
	 * Se os parâmetros não forem passados ao método, o retorno será <b>vazio</b>.
	 *
	 * <p style="color:red">Ordem de Parâmetros: JS-Head</p><br><br>
	 *		
	 * @param scriptOnHead
	 * @return
	 */
	public static String writeScriptTag(String scriptOnHead) {
		if (scriptOnHead != null){
			String scriptWrite = "<script>" + scriptOnHead + "</script>\n";
			return scriptWrite;
		}
		return "";
	}
	
	/**
	 * Método que retorna uma String com o conteúdo <b>&lt;meta&gt; [parâmetro metadataToHead (charset="UTF-8", por exemplo)]&gt;</b>.<br>
	 * Deve ser utilizado após a abertura da tag <b>&lt;head&gt;</b>.<br>
	 * Se os parâmetros não forem passados ao método, o retorno será <b>vazio</b>.
	 *
	 * <p style="color:red">Ordem de Parâmetros: Metadata-Name, Value-Meta</p><br><br>
	 *
	 * @param metadataOnHead
	 * @param value
	 * @return
	 */
	public static String writeMetaOnHead(String metadataOnHead, String value) {
		if (metadataOnHead != null){
			String metaWrite = "<meta " + metadataOnHead + "=\"" + value + "\">\n";
			return metaWrite;
		}
		return "";
	}
	
	/**
	 * Método que o corpo <b>&lt;head&gt;</b> completo, porém, suporta somente um <i>link import</i> e um <i>link script</i>,<br>
	 * caso ambos sejam declarados.
	 * <br><br>
	 * Como retorno, tem-se o retorno:<br>
	 * <i>&lt;head&gt; <br>
	 *   	&lt;linkRel="[parâmetro pathNamelinkRel]" href="[parâmetro hRef]"&gt; <br>
	 *   	&lt;script src="[parâmetro pathNameScriptHead]"&gt;&lt;/script&gt; <br>
	 *    &lt;/head&gt;</i>
	 * 
	 * <p style="color:red">Ordem de Parâmetros: href-CSS-File, Src-Path-JScript </p><br><br>
	 * 
	 * @param hRefCSS
	 * @param pathNameScriptHead
	 * @return
	 */
	public static String writeFullHead(String hRefCSS, String pathNameScriptHead) {
		return "<head>\n" + writeCssImportOnHead(hRefCSS) + writeScriptImportsOnHead(pathNameScriptHead) + "\n</head>\n";
	}
	
	/**
	 * Método que retorna uma String com o conteúdo <b>&lt;h1&gt;</b>, ou <b>&lt;h1&gt;[parâmetro content]</b>,<br>
	 * ou <b>&lt;h1 [parâmetro style (class="...", id="...", etc.)]&gt;</b> com terminação ou sem terminação da tag <b>&lt;/h1&gt;</b>.
	 *
	 * <p style="color:red">Ordem de Parâmetros: Content-h1, Inline-Props, Id, Class, hasTermination?</p><br><br>
	 *		
	 * @param content
	 * @param inLine
	 * @param tagId
	 * @param classCSSName
	 * @param hasTermination
	 * @return
	 */
	public static String writeH1Tag(String content, String inLine, String tagId, String classCSSName, Boolean hasTermination) {
		if (hasTermination == true){
			content = checkContent(content);
			return "<h1 " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content + "</h1>\n";
		} else {
			content = checkContent(content);
			return "<h1 " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content;	
		}
	}
	
	/**
	 * Método que retorna uma String com o conteúdo <b>&lt;h2&gt;</b>, ou <b>&lt;h2&gt;[parâmetro content]</b>,<br>
	 * ou <b>&lt;h2 [parâmetro style (class="...", id="...", etc.)]&gt;</b> com terminação ou sem terminação da tag <b>&lt;/h2&gt;</b>.
	 * 
	 * <p style="color:red">Ordem de Parâmetros: Content-h2, Inline-Props, Id, Class, hasTermination?</p><br><br>
	 * 
	 * @param content
	 * @param inLine
	 * @param tagId
	 * @param classCSSName
	 * @param hasTermination
	 * @return
	 */
	public static String writeH2Tag(String content, String inLine, String tagId, String classCSSName, Boolean hasTermination) {
		if (hasTermination == true){
			content = checkContent(content);
			return "<h2 " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content + "</h2>\n";
		} else {
			content = checkContent(content);
			return "<h2 " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content;	
		}
	}
	
	/**
	 * Método que retorna uma String com o conteúdo <b>&lt;h3&gt;</b>, ou <b>&lt;h3&gt;[parâmetro content]</b>,<br>
	 * ou <b>&lt;h3 [parâmetro style (class="...", id="...", etc.)]&gt;</b> com terminação ou sem terminação da tag <b>&lt;/h3&gt;</b>.
	 * 
	 * <p style="color:red">Ordem de Parâmetros: Content-h3, Inline-Props, Id, Class, hasTermination?</p><br><br>
	 * 
	 * @param content
	 * @param inLine
	 * @param tagId
	 * @param classCSSName
	 * @param hasTermination
	 * @return
	 */
	public static String writeH3Tag(String content, String inLine, String tagId, String classCSSName, Boolean hasTermination) {
		if (hasTermination == true){
			content = checkContent(content);
			return "<h3 " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content + "</h3>\n";
		} else {
			content = checkContent(content);
			return "<h3 " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content;	
		}
	}
	
	/**
	 * Método que retorna uma String com o conteúdo <b>&lt;h4&gt;</b>, ou <b>&lt;h4&gt;[parâmetro content]</b>,<br>
	 * ou <b>&lt;h4 [parâmetro style (class="...", id="...", etc.)]&gt;</b> com terminação ou sem terminação da tag <b>&lt;/h4&gt;</b>.
	 * 
	 * <p style="color:red">Ordem de Parâmetros: Content-h4, Inline-Props, Id, Class, hasTermination?</p><br><br>
	 * 
	 * @param content
	 * @param inLine
	 * @param tagId
	 * @param classCSSName
	 * @param hasTermination
	 * @return
	 */
	public static String writeH4Tag(String content, String inLine, String tagId, String classCSSName, Boolean hasTermination) {
		if (hasTermination == true){
			content = checkContent(content);
			return "<h4 " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content + "</h4>\n";
		} else {
			content = checkContent(content);
			return "<h4 " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content;	
		}
	}
	
	/**
	 * Método que retorna uma String com o conteúdo <b>&lt;p&gt;</b>, ou <b>&lt;p&gt;[parâmetro content]</b>,<br>
	 * ou <b>&lt;p [parâmetro style (class="...", id="...", etc.)]&gt;</b> com terminação ou sem terminação da tag <b>&lt;/p&gt;</b>.
	 * 
	 * <p style="color:red">Ordem de Parâmetros: Content-p, Inline-Props, Id, Class, hasTermination?</p><br><br>
	 * 
	 * @param content
	 * @param inLine
	 * @param tagId
	 * @param classCSSName
	 * @param hasTermination
	 * @return
	 */
	public static String writePTag(String content, String inLine, String tagId, String classCSSName, Boolean hasTermination) {
		if (hasTermination == true){
			content = checkContent(content);
			return "<p " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content + "</p>\n";
		} else {
			content = checkContent(content);
			return "<p " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content;	
		}
	}
	
	/**
	 * Método que retorna uma String com o conteúdo <b>&lt;a&gt;</b>, ou <b>&lt;a&gt; hRef="#" [parâmetro content]</b>,<br>
	 * ou <b>&lt;a hRef="#" [parâmetro style (class="...", id="...", etc.)]&gt;</b> com terminação ou sem terminação da tag <b>&lt;/a&gt;</b>.
	 * 
	 * <p style="color:red">Ordem de Parâmetros: Content-a, hRef, Inline-Props, Id, Class, hasTermination?</p><br><br>
	 * 
	 * @param content
	 * @param hRef
	 * @param inLine
	 * @param tagId
	 * @param classCSSName
	 * @param hasTermination
	 * @return
	 */
	public static String writeATag(String content, String hRef, String inLine, String tagId, String classCSSName, Boolean hasTermination) {
		if (hasTermination == true){
			content = checkContent(content);
			return "<a href=\"" + hRef +"\"" + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content + "</a>\n";
		} else {
			content = checkContent(content);
			return "<a href=\"" + hRef +"\"" + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content;	
		}
	}
	
	/**
	 * Método que retorna uma imagem como conteúdo <b>&lt;img&gt;</b>, sendo que o caminho do arquivo é passado como parâmetro.
	 * 
	 * <p style="color:red">Ordem de Parâmetros: Src-Img-File, Inline-Props, Id, Class, Alt</p><br><br>
	 * 
	 * @param pathFromImage
	 * @param inLine
	 * @param tagId
	 * @param classCSSName
	 * @param altContent
	 * @return
	 */
	public static String writeImgTag(String pathFromImage, String inLine, String tagId, String classCSSName, String altContent) {
		pathFromImage = checkContent(pathFromImage);
		return "<img src=" + "\"" + pathFromImage + "\" " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + " alt=\"" + altContent + "\"" + ">";	
	}
	
	/**
	 * Método que retorna uma String com o conteúdo <b>&lt;table&gt;</b>, ou <b>&lt;table&gt;[parâmetro content]</b>,<br>
	 * ou <b>&lt;table [parâmetro style (class="...", id="...", etc.)]&gt;</b> com terminação ou sem terminação da tag <b>&lt;/table&gt;</b>.
	 * 
	 * <p style="color:red">Ordem de Parâmetros: Content-table, Inline-Props, Id, Class, hasTermination?</p><br><br>
	 * 
	 * @param content
	 * @param inLine
	 * @param tagId
	 * @param classCSSName
	 * @param hasTermination
	 * @return
	 */
	public static String writeTableTag(String content, String inLine, String tagId, String classCSSName, Boolean hasTermination) {
		if (hasTermination == true){
			content = checkContent(content);
			return "<table " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content + "</table>\n";
		} else {
			content = checkContent(content);
			return "<table " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content;	
		}
	}
	
	/**
	 * Método que retorna uma String com o conteúdo <b>&lt;th&gt;</b>, ou <b>&lt;th&gt;[parâmetro content]</b>,<br>
	 * ou <b>&lt;th [parâmetro style (class="...", id="...", etc.)]&gt;</b> com terminação ou sem terminação da tag <b>&lt;/th&gt;</b>.
	 * 
	 * <p style="color:red">Ordem de Parâmetros: Content-th, Inline-Props, Id, Class, hasTermination?</p><br><br>
	 * 
	 * @param content
	 * @param inLine
	 * @param tagId
	 * @param classCSSName
	 * @param hasTermination
	 * @return
	 */
	public static String writeThTag(String content, String inLine, String tagId, String classCSSName, Boolean hasTermination) {
		if (hasTermination == true){
			content = checkContent(content);
			return "<th " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content + "</th>\n";
		} else {
			content = checkContent(content);
			return "<th " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content;	
		}
	}
	
	/**
	 * Método que retorna uma String com o conteúdo <b>&lt;td&gt;</b>, ou <b>&lt;td&gt;[parâmetro content]</b>,<br>
	 * ou <b>&lt;td [parâmetro style (class="...", id="...", etc.)]&gt;</b> com terminação ou sem terminação da tag <b>&lt;/td&gt;</b>.
	 * 
	 * <p style="color:red">Ordem de Parâmetros: Content-td, Inline-Props, Id, Class, hasTermination?</p><br><br>
	 * 
	 * @param content
	 * @param inLine
	 * @param tagId
	 * @param classCSSName
	 * @param hasTermination
	 * @return
	 */
	public static String writeTdTag(String content, String inLine, String tagId, String classCSSName, Boolean hasTermination) {
		if (hasTermination == true){
			content = checkContent(content);
			return "<td " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content + "</td>\n";
		} else {
			content = checkContent(content);
			return "<td " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content;	
		}
	}
	
	/**
	 * Método que retorna uma String com o conteúdo <b>&lt;ul&gt;</b>, ou <b>&lt;ul&gt;[parâmetro content]</b>,<br>
	 * ou <b>&lt;ul [parâmetro style (class="...", id="...", etc.)]&gt;</b> com terminação ou sem terminação da tag <b>&lt;/ul&gt;</b>.
	 * 
	 * <p style="color:red">Ordem de Parâmetros: Content-ul, Inline-Props, Id, Class, hasTermination?</p><br><br>
	 * 
	 * @param content
	 * @param inLine
	 * @param tagId
	 * @param classCSSName
	 * @param hasTermination
	 * @return
	 */
	public static String writeUlTag(String content, String inLine, String tagId, String classCSSName, Boolean hasTermination) {
		if (hasTermination == true){
			content = checkContent(content);
			return "<ul " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content + "</ul>\n";
		} else {
			content = checkContent(content);
			return "<ul " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content;	
		}
	}
	
	/**
	 * Método que retorna uma String com o conteúdo <b>&lt;li&gt;</b>, ou <b>&lt;li&gt;[parâmetro content]</b>,<br>
	 * ou <b>&lt;li [parâmetro style (class="...", id="...", etc.)]&gt;</b> com terminação ou sem terminação da tag <b>&lt;/li&gt;</b>.
	 * 
	 * <p style="color:red">Ordem de Parâmetros: Content-li, Inline-Props, Id, Class, hasTermination?</p><br><br>
	 * 
	 * @param content
	 * @param inLine
	 * @param tagId
	 * @param classCSSName
	 * @param hasTermination
	 * @return
	 */
	public static String writeLiTag(String content, String inLine, String tagId, String classCSSName, Boolean hasTermination) {
		if (hasTermination == true){
			content = checkContent(content);
			return "<li " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content + "</li>\n";
		} else {
			content = checkContent(content);
			return "<li " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content;	
		}
	}
	
	/**
	 * Método que retorna uma String com o conteúdo de uma tag definida pelo parâmetro <i>customTag</i>,<br>
	 * obtendo-se retorno como: <b>&lt;[customTag]&gt;</b>, ou <b>&lt;customTag&gt;[parâmetro content]</b>,<br>
	 * ou <b>&lt;customTag [parâmetro style (class="...", id="...", etc.)]&gt;[parâmetro content]</b> com terminação ou sem terminação da tag <b>&lt;/customTag&gt;</b>.
	 * <br>
	 * É possível utilizar este método para gerar tags que contenham diretivas (Angular, JSP, etc.).
	 * 
	 * <p style="color:red">Ordem de Parâmetros: New-Tag, Content-tag, Inline-Props, Id, Class, hasTermination?</p><br><br>
	 * 
	 * @param customTag
	 * @param content
	 * @param inLine
	 * @param tagId
	 * @param classCSSName
	 * @param hasTermination
	 * @return
	 */
	public static String writeCustomTag(String customTag, String content, String inLine, String tagId, String classCSSName, Boolean hasTermination) {
		if (hasTermination == true){
			content = checkContent(content);
			return "<" + customTag + " " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content + "</" + customTag + ">\n";
		} else {
			content = checkContent(content);
			return "<" + customTag + " " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content;
		}
	}

	/**
	 * Método para a escrita de Html puro.
	 * 
	 * <p style="color:red">Ordem de Parâmetros: Free-Html-Code</p><br><br>
	 * 
	 * @param htmlCode
	 * @return
	 */
	public static String writePureHtml(String htmlCode) {
		htmlCode = checkContent(htmlCode);
		return htmlCode;
	}
	
	/**
	 * Método que retorna uma String com o conteúdo de fechamento da tag <b>&lt;/head&gt;</b>.
	 *		
	 * @return
	 */
	public static String writeHeadClosed() {
		return "\n</head>\n";
	}

	/**
	 * Método que retorna uma String com o conteúdo de fechamento das tags <b>&lt;/body&gt;&lt;/html&gt;<b>.
	 * 
	 * @return
	 */
	public static String writeBodyAndHTMLClosed() {
		return "</body>\n</html>\n";
	}	
	
	/**
	 * Método que retorna uma String com o conteúdo de fechamento da tag <b>&lt;/p&gt;</b>.
	 *		
	 * @return
	 */
	public static String writePTagClosed() {
		return "\n</p>\n";
	}

	/**
	 * Método que retorna uma String com o conteúdo de fechamento da tag <b>&lt;/a&gt;</b>.
	 *		
	 * @return
	 */
	public static String writeATagClosed() {
		return "\n</a>\n";
	}
	
	/**
	 * Método que retorna uma String com o conteúdo de fechamento da tag <b>&lt;/li&gt;</b>.
	 *		
	 * @return
	 */
	public static String writeLiTagClosed() {
		return "\n</li>\n";
	}
	
	/**
	 * Método que retorna uma String com o conteúdo de fechamento da tag <b>&lt;/ul&gt;</b>.
	 *		
	 * @return
	 */
	public static String writeUlTagClosed() {
		return "\n</ul>\n";
	}
	
	/**
	 * Método que retorna uma String com o conteúdo de fechamento da tag <b>&lt;/div&gt;&lt;<b>.
	 * 
	 * <p style="color:red">Ordem de Parâmetros: New-Tag-End
	 * 
	 * @return
	 */
	public static String writeCustomTagClosed(String customTag) {
		return "\n</"+ customTag +">\n";
	}

	/**
	 * Método que retorna uma String com o conteúdo de fechamento da tag <b>&lt;/div&gt;&lt;<b>.
	 * 
	 * @return
	 */
	public static String writeBreakLine() {
		return "<br/>";
	}

	/**
	 * Método que retorna uma String com o conteúdo <b>&lt;div&gt;</b>, ou <b>&lt;div&gt;[parâmetro content]</b>,<br>
	 * ou <b>&lt;div [parâmetro style (class="...", id="...", etc.)]&gt;</b> com terminação ou sem terminação da tag <b>&lt;/div&gt;</b>.
	 * 
	 * <p style="color:red">Ordem de Parâmetros: Content-div, Inline-Props, Id, Class</p><br><br>
	 * 
	 * @param content
	 * @param inLine
	 * @param tagId
	 * @param classCSSName
	 * @return
	 */
	public static String writeDivTag(String content, String inLine, String tagId, String classCSSName) {
		content = checkContent(content);
		return "<div " + checkInLineContent(inLine) + checkTagIdName(tagId) + checkCSSClassName(classCSSName) + ">" + content;	
	}
	
	/**
	 * Método que retorna uma String com o conteúdo de fechamento da tag <b>&lt;/div&gt;&lt;<b>.
	 * 
	 * @return
	 */
	public static String writeDivClosed() {
		return "\n</div>\n";
	}
	
	/**
	 * Método que retorna uma String com o conteúdo de fechamento da tag <b>&lt;/div&gt;&lt;<b> mais a descrição do fechamento
	 * para facilitar sua localização de referência no HTML.
	 * 
	 * <p style="color:red">Ordem de Parâmetros: Description-Div-Name</p><br><br>
	 * 
	 * @param descriptionTag
	 * @return
	 */
	public static String writeDivClosed(String descriptionTag) {
		return "\n</div><!-- End of Div:" + descriptionTag + " -->\n";
	}
	
	/**
	 * Método que realiza a inserção (append) do conteúdo/log a um arquivo HTML.<br>
	 * caso não seja informado o path do arquivo a ser ou já criado, será retonado <br>
	 * <i>"Undefined_HTML_File"</i> ao FileWriter do arquivo.
	 * 
	 *  <p style="color:red">Ordem de Parâmetros: String-Html-Path-of-File, Tag-Content, html-File-Instance</p><br><br>
	 *  
	 * @param htmlFile
	 * @param contentOutput
	 * @param pathAndNameForFileCreation
	 */
	public static void appendTag(File htmlFile, String contentOutput, String pathAndNameForFileCreation) {
		BufferedWriter bufferWriter = null;
		FileWriter fileWriter = null;
		PrintWriter dataOutput = null;
		String htmlExtension = FileDirPRM.Extension.HTML_FILE;
		try {
			pathAndNameForFileCreation = checkPathFile(pathAndNameForFileCreation);
			fileWriter = new FileWriter(pathAndNameForFileCreation + htmlExtension, true);
			bufferWriter = new BufferedWriter(fileWriter);
			dataOutput = new PrintWriter(bufferWriter);
			dataOutput.append(contentOutput);
		} catch (IOException io) {
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.IO_ERROR + htmlFile.getName()), io);
		} finally {
			try {
				checkFileWriterParameters(bufferWriter, fileWriter, dataOutput);
			} catch (IOException io) {
				throw new RunningException(BaseLogger.logException(MessagePRM.AsException.IO_FATAL_ERROR + htmlFile.getName()), io);
			}
		}
	}

	private static void checkFileWriterParameters(BufferedWriter bufferWriter,
			FileWriter fileWriter, PrintWriter dataOutput) throws IOException {
		if (dataOutput != null) {
			dataOutput.close();
		} else if (bufferWriter != null) {
			bufferWriter.close();
		} else if (fileWriter != null) {
			fileWriter.close();
		}
	}

	private static String checkPathFile(String pathAndNameForFileCreation) {
		if (pathAndNameForFileCreation == null){
			pathAndNameForFileCreation = "Undefined_HTML_File";
		}
		return pathAndNameForFileCreation;
	}
	   
	private static String checkTagIdName(String tagId) {
		if (tagId != null){
			String idName=" id=\""+tagId+"\"";
			return idName;
		} else {
			return "";
		}
	}
	
	private static String checkCSSClassName(String classCSSName) {
		if (classCSSName != null){
			String className=" class=\""+classCSSName+"\"";
			return className;
		} else {
			return "";
		}
	}
	
	private static String checkInLineContent(String inLine) {
		if (inLine != null){
			return " " + inLine;
		} else {
			return "";
		}
	}	
	
	private static String checkContent(String content) {
		if (content == null){
			content = "";
		}
		return content;
	}
	
}
