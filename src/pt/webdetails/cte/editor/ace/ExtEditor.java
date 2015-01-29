package pt.webdetails.cte.editor.ace;

import org.apache.commons.lang.StringUtils;
import org.pentaho.platform.util.messages.LocaleHelper;
import pt.webdetails.cpf.context.api.IUrlProvider;
import pt.webdetails.cpf.packager.origin.PathOrigin;
import pt.webdetails.cpf.packager.origin.StaticSystemOrigin;
import pt.webdetails.cpf.repository.api.IContentAccessFactory;
import pt.webdetails.cpf.utils.Pair;

import java.io.IOException;
import java.util.ArrayList;

public class ExtEditor extends ProcessedHtmlPage {

  private static String EXT_EDITOR = "ext-editor.html";
  private static String MAIN_EDITOR = "editor.html";
  private static String UI_BACKEND_PREFIX = "ExternalEditor.";
  private static String EDITOR_SYS_DIR = "resources/editor";

  public ExtEditor( IUrlProvider urlProvider, IContentAccessFactory access ) {
    super( urlProvider, access );
  }

  public String getMainEditor() throws IOException {
    return processPage( getBaseDir(), MAIN_EDITOR );
  }
  public String getExtEditor() throws IOException {
    return processPage( getBaseDir(), EXT_EDITOR );
  }

  private PathOrigin getBaseDir() {
    return new StaticSystemOrigin( EDITOR_SYS_DIR );
  }

  protected Iterable<Pair<String, String>> getBackendAssignments( IUrlProvider urlProvider ) {
    String baseApi =  urlProvider.getPluginBaseUrl();
    ArrayList<Pair<String, String>> pairs = new ArrayList<Pair<String,String>>();
    pairs.add( new Pair<String, String>( UI_BACKEND_PREFIX + "EXT_EDITOR", quote( baseApi, "edit" ) ) );
    pairs.add( new Pair<String, String>( UI_BACKEND_PREFIX + "CAN_EDIT_URL", quote( baseApi, "canEdit") ) );
    pairs.add( new Pair<String, String>( UI_BACKEND_PREFIX + "GET_FILE_URL", quote( baseApi, "getFile" ) ) );
    pairs.add( new Pair<String, String>( UI_BACKEND_PREFIX + "SAVE_FILE_URL", quote( baseApi, "save" ) ) );
    pairs.add(
      new Pair<String, String>(
        UI_BACKEND_PREFIX + "LANG_PATH",
        quote( urlProvider.getPluginStaticBaseUrl(), EDITOR_SYS_DIR, "/languages/" ) ) );

    pairs.add( new Pair<String, String>( UI_BACKEND_PREFIX + "LOCALE", quote( LocaleHelper.getLocale().toString() ) ) );

    pairs.add( new Pair<String, String>( UI_BACKEND_PREFIX + "STATUS.OK", "true" ) );

    return pairs;
  }

  private String quote( String...text ) {
    return '"' + StringUtils.join(text) + '"';
  }
}
