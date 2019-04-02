package com.gwt.wrapper.lo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTWrapper implements EntryPoint {
	public void onModuleLoad() {

		showChatButton(); // This logic should be added before tracking code to specifc pages where we
							// want to show chat button by default
//		hideChatButton();

		RootPanel.get().add(new Button("Chat", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				startChat();
			}
		}));

		RootPanel.get().add(new Button("Add Tag - tag1", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addTag("tag1", false, false);
			}
		}));

		RootPanel.get().add(new Button("Add Tag - tag2", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addTag("tag2", false, false);
			}
		}));

		RootPanel.get().add(new Button("Add Tag - tag3", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addTag("tag3", false, false);
			}
		}));

		RootPanel.get().add(new Button("New Page", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				newPage("title", "http://testUrl.com");
			}
		}));

		

		onAgentUnavailable(new GWTWrapper.Handler() {

			@Override
			public void onChange() {
				Window.alert("_lo_chat_agent_unavailable");

			}
		});
		onAgentAvailable(new GWTWrapper.Handler() {

			@Override
			public void onChange() {
				Window.alert("lo_chat_agent_available");

			}
		});
		initializeScripts();

	}

	private native void showChatButton() /*-{
		console.log('showChatButton');
		$wnd.__wtw_lucky_show_chat_box = true;
	}-*/;

	private native void hideChatButton()/*-{
		console.log('hideChatButton');
		$wnd.__wtw_lucky_no_chat_box = true;
	}-*/;

	private native void addTag(String tagName, boolean star, boolean overwrite) /*-{
		$wnd._loq.push([ "tag", tagName, star, overwrite ]);
	}-*/;

	private void initializeScripts() {
		initializeSiteId(148574); // TODO Update Site ID
		Element head = Document.get().getElementsByTagName("head").getItem(0);
		ScriptElement sce = Document.get().createScriptElement();
		sce.setType("text/javascript");
		sce.setSrc("https://d10lpsik1i8c69.cloudfront.net/w.js"); // TODO Check URL
		head.appendChild(sce);

	}

	private native void initializeSiteId(int id) /*-{
		$wnd.__lo_site_id = id;
	}-*/;

	private native void onAgentUnavailable(Handler handler) /*-{
		$wnd.lo_chat_agent_unavailable = $entry(function() {
			console.log('_lo_chat_agent_unavailable');
			handler.@com.gwt.wrapper.lo.client.GWTWrapper.Handler::onChange()();
		});
	}-*/;

	private native void onAgentAvailable(Handler handler) /*-{
		$wnd.lo_chat_agent_available = $entry(function() {
			console.log('_lo_chat_agent_available');
			handler.@com.gwt.wrapper.lo.client.GWTWrapper.Handler::onChange()();
		});
	}-*/;

	private native void startChat() /*-{
		$wnd.LO.page();
	}-*/;

	private native void newPage(String title, String url) /*-{
		$wnd.__wtw_lucky_override_save_url = url;
		$wnd.LO.new_page(title);
	}-*/;

	public interface Handler {
		public void onChange();
	}

}
