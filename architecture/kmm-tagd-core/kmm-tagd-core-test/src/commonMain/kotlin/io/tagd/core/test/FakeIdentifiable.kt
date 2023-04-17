package io.tagd.core.test

import io.tagd.core.Identifiable

class FakeIdentifiable(override val identifier: String) : Identifiable<String>