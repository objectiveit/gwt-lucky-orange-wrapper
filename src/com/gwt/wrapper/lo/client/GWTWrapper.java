package com.gwt.wrapper.lo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;

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

		RootPanel.get().add(new Button("PassCustomData", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JSONObject obj = new JSONObject();
				obj.put("name", new JSONString("John Doe"));
				obj.put("email", new JSONString("email@example.com"));
				obj.put("whatever1", new JSONString("Anything here"));
				customData(obj.getJavaScriptObject());
			}

		}));

		RootPanel.get().add(new Button("Make header sensitive", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addSensitiveStyling(DOM.getElementById("h1"), true);
			}

		}));

		RootPanel.get().add(new Button("Away", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				triggerAwayFrom();
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
		onChatEndedByOperator(new GWTWrapper.Handler() {
			@Override
			public void onChange() {
				Window.alert("lo_chat_ended_by_operator");

			}
		});

		onChatEndedByVisitor(new GWTWrapper.Handler() {
			@Override
			public void onChange() {
				Window.alert("lo_chat_ended_by_visitor");

			}
		});

		onChatInitiated(new GWTWrapper.Handler() {
			@Override
			public void onChange() {
				Window.alert("lo_chat_ended_by_visitor");

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

	private native void customData(JavaScriptObject obj)/*-{
		$wnd._loq.push([ "custom", obj ]);
	}-*/;

	private native void triggerAwayFrom()/*-{
		$wnd.LO.away_form();
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

	private native void onChatInitiated(Handler handler) /*-{
		$wnd.lo_chat_initiated = $entry(function(asked_to_chat) {
			console.log('_lo_chat_initiated ');
			console.log(asked_to_chat);
			handler.@com.gwt.wrapper.lo.client.GWTWrapper.Handler::onChange()();
		});
	}-*/;

	private native void onAgentAvailable(Handler handler) /*-{
		$wnd.lo_chat_agent_available = $entry(function() {
			console.log('_lo_chat_agent_available');
			handler.@com.gwt.wrapper.lo.client.GWTWrapper.Handler::onChange()();
		});
	}-*/;

	private native void onChatEndedByOperator(Handler handler) /*-{
		$wnd.lo_chat_ended_by_operator = $entry(function() {
			console.log('_lo_chat_ended_by_operator ');
			handler.@com.gwt.wrapper.lo.client.GWTWrapper.Handler::onChange()();
		});
	}-*/;

	private native void onChatEndedByVisitor(Handler handler) /*-{
		$wnd.lo_chat_ended_by_visitor = $entry(function() {
			console.log('_lo_chat_ended_by_visitor  ');
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

	public static void addSensitiveStyling(UIObject uiObject, boolean isSensitive) {
		uiObject.addStyleName(isSensitive ? "LoSensitive" : "LoNotSensitive");
	}

	public static void addSensitiveStyling(Element element, boolean isSensitive) {
		element.addClassName(isSensitive ? "LoSensitive" : "LoNotSensitive");
	}

}
