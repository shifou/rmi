/*
 * Define msgType for different kinds of message
 */
package data;

public enum msgType {
	REBIND,
	REBINDOK,
	REBINDERROR,
	LOOKUP,
	LOOKUPERROR,
	LOOKUPOK,
	LIST,
	INVOKE,
	REPLY,
	INVOKEOK,
	INVOKEERROR
}