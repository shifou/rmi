/*
 * Define msgType for different kinds of message
 */
package data;

public enum msgType {
	REBIND,
	LOOKUP,
	LOOKUPERROR,
	LOOKUPOK,
	LIST,
	INVOKE,
	REPLY,
	INVOKEOK,
	INVOKEERROR
}