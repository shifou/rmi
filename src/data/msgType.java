/*
 * Define msgType for different kinds of message
 */
package data;

public enum msgType {
	BIND,
	BINDOK,
	BINDERROR,
	UNBIND,
	UNBINDOK,
	UNBINDERROR,
	REBIND,
	REBINDOK,
	REBINDINFO,
	LOOKUP,
	LOOKUPERROR,
	LOOKUPOK,
	LIST,
	INVOKE,
	REPLY,
	INVOKEOK,
	INVOKEERROR,
}