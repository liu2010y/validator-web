/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.devefx.validator.internal.engine.messageinterpolation.parser;

/**
 * @author Hardy Ferentschik
 */
public class ELState implements ParserState {

    @Override
    public void start(TokenCollector tokenCollector) {
        throw new IllegalStateException("Parsing of message descriptor cannot start in this state");
    }

    @Override
    public void terminate(TokenCollector tokenCollector) throws MessageDescriptorFormatException {
        tokenCollector.appendToToken(TokenCollector.EL_DESIGNATOR);
        tokenCollector.terminateToken();
    }

    @Override
    public void handleNonMetaCharacter(char character, TokenCollector tokenCollector)
            throws MessageDescriptorFormatException {
        tokenCollector.appendToToken(TokenCollector.EL_DESIGNATOR);
        tokenCollector.appendToToken(character);
        tokenCollector.terminateToken();
        tokenCollector.transitionState(new BeginState());
        tokenCollector.next();
    }

    @Override
    public void handleBeginTerm(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
        tokenCollector.terminateToken();

        tokenCollector.appendToToken(TokenCollector.EL_DESIGNATOR);
        tokenCollector.appendToToken(character);
        tokenCollector.makeELToken();
        tokenCollector.transitionState(new InterpolationTermState());
        tokenCollector.next();
    }

    @Override
    public void handleEndTerm(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
        throw new MessageDescriptorFormatException("The message descriptor '" + tokenCollector.getOriginalMessageDescriptor() +
                "' contains an unbalanced meta character '" + character + "' parameter.");
    }

    @Override
    public void handleEscapeCharacter(char character, TokenCollector tokenCollector)
            throws MessageDescriptorFormatException {
        tokenCollector.transitionState(new EscapedState(this));
        tokenCollector.next();
    }

    @Override
    public void handleELDesignator(char character, TokenCollector tokenCollector)
            throws MessageDescriptorFormatException {
        handleNonMetaCharacter(character, tokenCollector);
    }
}


