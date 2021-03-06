/**
 * Copyright 2011 IMAGEM Solutions TI sant
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gwtplatform.plugin.projectfile.src.client.gin;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import com.gwtplatform.plugin.SourceWriter;
import com.gwtplatform.plugin.SourceWriterFactory;
import com.gwtplatform.plugin.projectfile.ProjectClass;

/**
 *
 * @author Michael Renaud
 */
public class Ginjector extends ProjectClass {

  private static final String C_DISPATCH_ASYNC_MODULE = "com.gwtplatform.dispatch.client.gin.DispatchAsyncModule";
  private static final String C_PROVIDER = "com.google.inject.Provider";
  private static final String I_ASYNC_PROVIDER = "com.google.gwt.inject.client.AsyncProvider";
  private static final String I_PLACE_MANAGER = "com.gwtplatform.mvp.client.proxy.PlaceManager";
  private static final String A_GIN_MODULES = "com.google.gwt.inject.client.GinModules";
  private static final String I_EVENT_BUS = "com.google.web.bindery.event.shared.EventBus";
  private static final String I_GINJECTOR = "com.google.gwt.inject.client.Ginjector";

  private final IType presenterModule;

  public Ginjector(IPackageFragmentRoot root, String fullyQualifiedName,
      SourceWriterFactory sourceWriterFactory) throws JavaModelException {
    super(root, fullyQualifiedName, sourceWriterFactory);
    this.presenterModule = null;
  }

  public Ginjector(IPackageFragmentRoot root, String packageName, String elementName,
      IType presenterModule, SourceWriterFactory sourceWriterFactory) throws JavaModelException {
    super(root, packageName, elementName, sourceWriterFactory);
    this.presenterModule = presenterModule;
    init();
  }

  @Override
  protected IType createType() throws JavaModelException {
	  SourceWriter sw = sourceWriterFactory.createForNewClass();

	  sw.writeLines(
			  "@GinModules({ DispatchAsyncModule.class, " + presenterModule.getElementName()
			  + ".class })",
			  "public interface " + elementName + " extends Ginjector {",
			  "}");

	  IType result = workingCopy.createType(sw.toString(), null, false, new NullProgressMonitor());
	  workingCopy.createImport(A_GIN_MODULES, null, new NullProgressMonitor());
	  workingCopy.createImport(C_DISPATCH_ASYNC_MODULE, null, new NullProgressMonitor());
	  workingCopy.createImport(presenterModule.getFullyQualifiedName(), null, new NullProgressMonitor());
	  workingCopy.createImport(I_GINJECTOR, null, new NullProgressMonitor());

	  return result;
  }

  public IMethod[] createDefaultGetterMethods() throws JavaModelException {
    IMethod[] methods = new IMethod[3];

    workingCopy.createImport(I_EVENT_BUS, null, new NullProgressMonitor());
    methods[0] = workingCopyType.createMethod("EventBus getEventBus();", null, false, new NullProgressMonitor());

    workingCopy.createImport(I_PLACE_MANAGER, null, new NullProgressMonitor());
    methods[1] = workingCopyType.createMethod("PlaceManager getPlaceManager();", null, false, new NullProgressMonitor());

    return methods;
  }

  public IMethod createProvider(IType presenter) throws JavaModelException {
    IAnnotation annotation = null;
    IJavaElement[] children = presenter.getChildren();
    for (IJavaElement child : children) {
      if (child instanceof IType && ((IType) child).getElementName().equals("MyProxy")) {
        annotation = ((IType) child).getAnnotation("ProxyStandard");
        break;
      }
    }

    workingCopy.createImport(annotation.exists() ? C_PROVIDER : I_ASYNC_PROVIDER, null, new NullProgressMonitor());
    workingCopy.createImport(presenter.getFullyQualifiedName(), null, new NullProgressMonitor());

    SourceWriter sw = sourceWriterFactory.createForNewClassBodyComponent();
    sw.writeLine(
        (annotation.exists() ? "Provider<" : "AsyncProvider<")
        + presenter.getElementName() + "> get" + presenter.getElementName() + "();");

    return createMethod(sw);
  }

}
