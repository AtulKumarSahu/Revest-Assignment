import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        KoinHelperKt.initKoinIos()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
